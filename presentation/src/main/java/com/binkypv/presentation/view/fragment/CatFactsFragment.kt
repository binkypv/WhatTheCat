package com.binkypv.presentation.view.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.binkypv.presentation.R
import com.binkypv.presentation.databinding.DialogSaveFileBinding
import com.binkypv.presentation.databinding.FragmentCatFactsBinding
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private const val LOADING_FLIPPER_VIEW = 0
private const val IMAGE_FLIPPER_VIEW = 1

private const val SHARED_IMAGE_FILENAME = "shared_cat"
private const val FILE_PROVIDER_AUTHORITY = "com.binkypv.whatthecat.provider"
private const val MIME_TYPE = "image/jpg"

@RuntimePermissions
class CatFactsFragment : BaseFragment<FragmentCatFactsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatFactsBinding
        get() = FragmentCatFactsBinding::inflate

    private val catFactsViewModel: CatFactsViewModel by viewModel()
    var savedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        catFactsViewModel.onGetNewCatFact()
    }

    private fun initListeners() {
        binding.catButton.setOnClickListener {
            catFactsViewModel.onGetNewCatFact()
        }
    }

    private fun initObservers() {
        catFactsViewModel.loading.observe(viewLifecycleOwner, {
            binding.catHeader.displayedChild = LOADING_FLIPPER_VIEW
            binding.catFactText.visibility = View.GONE
        })

        catFactsViewModel.fact.observe(viewLifecycleOwner, {
            binding.catFactText.text = it.fact
            binding.catFactText.visibility = View.VISIBLE
        })

        catFactsViewModel.img.observe(viewLifecycleOwner, {
            Glide.with(binding.catImage)
                .load(it.url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.catImage)
            binding.catHeader.displayedChild = IMAGE_FLIPPER_VIEW
        })
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            saveImageWithPermissionCheck(
                binding.catImage.drawable.toBitmap(),
                SHARED_IMAGE_FILENAME,
                true
            )
            true
        }
        R.id.action_save -> {
            showSavingDialog()
            true
        }
        else -> {
            false
        }
    }

    private fun showSavingDialog() {
        val dialogBinding: DialogSaveFileBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_save_file, null, false
        )

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setView(dialogBinding.root)
            .setPositiveButton(
                getString(R.string.dialog_positive_button)
            ) { _, _ ->
                dialogBinding.dialogText.text.toString().let {
                    if (it.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.toast_empty_filename),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        saveImage(
                            binding.catImage.drawable.toBitmap(),
                            it,
                            false
                        )
                    }
                }

            }
            .setNegativeButton(getString(R.string.dialog_negative_button), null)
            .create();
        dialog.show();
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage(bitmap: Bitmap, name: String, share: Boolean = false) {
        var fos: OutputStream? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) getOutputStreamAndroidQ(name) else getOutputStream(
                name
            )
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos?.close()
        if (share) sendShareIntent()
    }

    private fun getOutputStreamAndroidQ(name: String): OutputStream? {
        val resolver: ContentResolver = requireActivity().contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            )
        }
        val imageUri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        savedImageUri = imageUri

        return resolver.openOutputStream(imageUri ?: Uri.EMPTY, "rwt")
    }

    private fun getOutputStream(name: String): OutputStream {
        val imagesDir: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString()
        val image = File(imagesDir, "$name.jpg")
        savedImageUri = FileProvider.getUriForFile(
            requireContext(),
            FILE_PROVIDER_AUTHORITY,
            image
        )
        return FileOutputStream(image, false)
    }

    @OnPermissionDenied
    fun showPermissionDeniedToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.permission_not_granted),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun sendShareIntent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, binding.catFactText.text)
            putExtra(Intent.EXTRA_STREAM, savedImageUri)
            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            type = MIME_TYPE
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.sharing_message)))
    }
}
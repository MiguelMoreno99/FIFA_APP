package com.example.diyapp.ui.newpublication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.databinding.FragmentNewPublicationBinding
import com.example.diyapp.ui.viewmodel.NewPublicationViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewPublicationViewModel by viewModels()
    private val imageUris = mutableListOf<Uri>()
    private lateinit var recyclerViewAdapter: MultipleImagesAdapter
    private lateinit var barcodeLauncher: ActivityResultLauncher<ScanOptions>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPublicationBinding.inflate(layoutInflater, container, false)
        return binding.root
            }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScanner()

        setupSpinner()
        setupRecyclerView()
        setupImagePickers()
        setupListeners()

        observeViewModel()

        val sharedPref = SessionManager.getUserInfo(requireContext())
        viewModel.setUserEmail(sharedPref["CORREO_USUARIO"] ?: "")
    }

    private fun setupSpinner() {
        val options = resources.getStringArray(R.array.category_options)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerOptions.adapter = spinnerAdapter
    }

    private fun setupRecyclerView() {
//        recyclerViewAdapter = MultipleImagesAdapter(imageUris)
//        binding.recyclerViewInstructionPhotos.apply {
//            adapter = recyclerViewAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupImagePickers() {
//        val pickMainPhoto =
//            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
//                uri?.let { binding.imageViewMainPhoto.setImageURI(it) }
//            }

        val pickMultiplePhotos =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    imageUris.clear()
                    imageUris.addAll(uris)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

//        binding.btnUploadImage.setOnClickListener {
//            pickMainPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
//
//        binding.btnUploadMultipleImages.setOnClickListener {
//            pickMultiplePhotos.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
    }

    private fun setupListeners() {
//        binding.btnPost.setOnClickListener {
//            val title = binding.etTitle.text.toString()
//            val description = binding.etDescription.text.toString()
//            val theme = binding.spinnerOptions.selectedItem.toString()
//            val instructions = binding.etInstructions.text.toString()
//            val mainPhoto = ImageUtils.bitmapToBase64(binding.imageViewMainPhoto.drawToBitmap())
//            val photos = recyclerViewAdapter.getImagesAsBase64(requireContext())
//            lifecycleScope.launch {
//                viewModel.createPublication(
//                    title,
//                    description,
//                    theme,
//                    instructions,
//                    mainPhoto,
//                    photos
//                )
//            }
//        }
    }

    private fun observeViewModel() {
        viewModel.isPublicationCreated.observe(viewLifecycleOwner) { success ->
            if (success) {
                SessionManager.showToast(requireContext(), R.string.publicationCreated)
                findNavController().navigate(R.id.myPublicationsFragment)
            } else {
                SessionManager.showToast(requireContext(), R.string.error2)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { messageResId ->
            messageResId?.let {
                SessionManager.showToast(requireContext(), it)
            }
        }
    }

    private fun setupScanner(){
        barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents == null) {
                SessionManager.showToast(requireContext(), R.string.scanCanceled)
            } else {
                // We have a result!
                // The QR code content is in result.contents
                SessionManager.showToast(requireContext(), R.string.scanCompleted)

                // TODO: Aquí va tu lógica para procesar el código de la estampa.
                // Por ejemplo, verificar el código y añadir la estampa a la cuenta del usuario.
                Toast.makeText(requireContext(),result.contents,Toast.LENGTH_LONG).show()
            }
        }
        binding.scanButton.setOnClickListener { initScanner() }
    }
    private fun initScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Point to your QR Code!") // Message shown to the user
        options.setCameraId(0) // Use the rear camera
        options.setBeepEnabled(true) // A beep sound on successful scan
        options.setBarcodeImageEnabled(true) // Saves an image of the scanned code
        options.setOrientationLocked(true) // Allows orientation changes

        // Launch the scanner
        barcodeLauncher.launch(options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package co.za.androidalien.floward.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.za.androidalien.floward.databinding.FragmentUserDetailBinding
import co.za.androidalien.floward.ui.adapter.PostListAdapter
import co.za.androidalien.floward.ui.viewmodel.MainActivityViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val sharedViewModel: MainActivityViewModel by sharedViewModel()
    private lateinit var adapter: PostListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        adapter = PostListAdapter()
        binding.postList.adapter = adapter

        sharedViewModel.getPostsByUser();
        sharedViewModel.posts.observe(viewLifecycleOwner) {
            //update adapter
            adapter.submitList(it)
        }

        Glide.with(this).load(sharedViewModel.selectedUser?.url)
            .centerCrop().into(binding.userImage)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
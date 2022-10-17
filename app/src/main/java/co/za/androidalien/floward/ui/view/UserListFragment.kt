package co.za.androidalien.floward.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.za.androidalien.floward.R
import co.za.androidalien.floward.databinding.FragmentUserListBinding
import co.za.androidalien.floward.data.models.User
import co.za.androidalien.floward.ui.adapter.UserListAdapter
import co.za.androidalien.floward.ui.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val sharedViewModel: MainActivityViewModel by sharedViewModel()
    private lateinit var adapter: UserListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        adapter = UserListAdapter { user -> onClick(user) }
        binding.userList.adapter = adapter
        sharedViewModel.initializeData()

        sharedViewModel.users.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it as MutableList<User>)
            }
        }
        return binding.root
    }

    private fun onClick(user: User) {
        sharedViewModel.selectedUser = user
        findNavController().navigate(R.id.action_UserListFragment_to_UserDetailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
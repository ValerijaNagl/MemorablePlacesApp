package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.fragments.ListFragment
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.fragments.MapFragment


class PagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> MapFragment()
            else -> ListFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> "Mapa"
            else -> "Lista"
        }
    }

}
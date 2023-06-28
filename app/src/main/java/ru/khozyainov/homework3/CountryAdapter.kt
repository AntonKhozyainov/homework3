package ru.khozyainov.homework3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.khozyainov.homework3.databinding.ItemCountryBinding

class CountryAdapter: RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder =
        CountryHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: CountryHolder, position: Int) = holder.bind(position)

    class CountryHolder(
        private val binding: ItemCountryBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val country = countryList[position]
            with(binding){
                countryIdTextView.text = country.id.toString()
                countryNameTextView.text = country.name

                Glide.with(itemView)
                    .load(country.flagImageUrl)
                    .error(android.R.drawable.stat_notify_error)
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .into(flagImageView)
            }
        }
    }

    companion object{
        private const val BASE_IMAGE_URL = "https://flagsapi.com/{c}/flat/64.png"

        private val countryList = listOf(
            Country(id = 1, name = "Andorra", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AD") ),
            Country(id = 2, name = "Afghanistan", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AF") ),
            Country(id = 3, name = "Anguilla", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AI") ),
            Country(id = 4, name = "Albania", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AL") ),
            Country(id = 5, name = "Armenia", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AM") ),
            Country(id = 6, name = "Angola", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AO") ),
            Country(id = 7, name = "Antarctica", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AQ") ),
            Country(id = 8, name = "Argentina", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AR") ),
            Country(id = 9, name = "Austria", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AT") ),
            Country(id = 10, name = "Australia", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AU") ),
            Country(id = 11, name = "Azerbaijan", flagImageUrl = BASE_IMAGE_URL.replace("{c}","AZ") ),
            Country(id = 12, name = "Barbados", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BB") ),
            Country(id = 13, name = "Bangladesh", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BD") ),
            Country(id = 14, name = "Bulgaria", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BG") ),
            Country(id = 15, name = "Benin", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BJ") ),
            Country(id = 16, name = "Bolivia", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BO") ),
            Country(id = 17, name = "Brazil", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BR") ),
            Country(id = 18, name = "Bhutan", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BT") ),
            Country(id = 19, name = "Belarus", flagImageUrl = BASE_IMAGE_URL.replace("{c}","BY") ),
            Country(id = 20, name = "Canada", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CA") ),
            Country(id = 21, name = "Congo", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CG") ),
            Country(id = 22, name = "Switzerland", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CH") ),
            Country(id = 23, name = "Cameroon", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CM") ),
            Country(id = 24, name = "China", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CN") ),
            Country(id = 25, name = "Colombia", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CO") ),
            Country(id = 26, name = "Costa Rica", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CR") ),
            Country(id = 27, name = "Cuba", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CU") ),
            Country(id = 28, name = "Cyprus", flagImageUrl = BASE_IMAGE_URL.replace("{c}","CY") ),
            Country(id = 29, name = "Germany", flagImageUrl = BASE_IMAGE_URL.replace("{c}","DE") ),
            Country(id = 30, name = "Dominica", flagImageUrl = BASE_IMAGE_URL.replace("{c}","DM") ),
        )
    }
}
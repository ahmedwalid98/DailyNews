package com.example.dailynews.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.databinding.ItemCategoryLeftSidedBinding
import com.example.dailynews.databinding.ItemCategoryRightSidedBinding

class CategoryAdapter(private val categories: List<Category>,private val onCategoryClicked: OnCategoryClicked):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==isLeftSided){
            ViewHolderLeft.from(parent)
        }else{
            ViewHolderRight.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cat = categories[position]
        if(getItemViewType(position)==isLeftSided){
            ( holder as ViewHolderLeft).bind(cat)

        }else{
            ( holder as ViewHolderRight).bind(cat)
        }
        holder.itemView.setOnClickListener {
            onCategoryClicked.onCategoryClicked(cat)
        }
    }

    override fun getItemCount(): Int = categories.size

    private val isRightSided = 10;
    private val isLeftSided = 20;


    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0) return isLeftSided;
        return isRightSided;
    }
    class ViewHolderLeft(val view : ItemCategoryLeftSidedBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(model : Category){
            view.model=model
            view.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolderLeft{
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemCategoryLeftSidedBinding.inflate(inflater,parent,false)
                return ViewHolderLeft(view)
            }
        }
    }
    class ViewHolderRight(val view : ItemCategoryRightSidedBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(model : Category){
            view.model=model
            view.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolderRight{
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemCategoryRightSidedBinding.inflate(inflater,parent,false)
                return ViewHolderRight(view)
            }
        }
    }

    class OnCategoryClicked(val onItemClicked:(category:Category) ->Unit){
        fun onCategoryClicked(category: Category) = onItemClicked(category)
    }

}
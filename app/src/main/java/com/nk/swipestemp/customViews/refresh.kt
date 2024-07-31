import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

//Custom refresh layout without memory leak
class ViewPagerSwipeRefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SwipeRefreshLayout(context, attrs) {

	private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
	private var previousX = 0F
	private var declined: Boolean = false

	override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				previousX = event.x
				declined = false
			}

			MotionEvent.ACTION_MOVE -> {
				val eventX = event.x
				val xDiff = abs(eventX - previousX)

				if (declined || xDiff > touchSlop) {
					declined = true
					return false
				}
			}
		}
		return super.onInterceptTouchEvent(event)
	}
}

//Recyclerview
//swipeRefreshLayout.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//				swipeRefreshLayout?.isEnabled = isSwipeRefreshEnabled(recyclerView)
//			}
//		})

private fun isSwipeRefreshEnabled(recyclerView: RecyclerView?): Boolean {
		val topRowVerticalPosition =
			if (recyclerView == null || recyclerView.childCount == 0) 0
			else recyclerView.getChildAt(0).top
		return topRowVerticalPosition >= 0
	}
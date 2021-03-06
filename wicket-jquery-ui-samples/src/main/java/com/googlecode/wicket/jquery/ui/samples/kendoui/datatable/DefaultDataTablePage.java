package com.googlecode.wicket.jquery.ui.samples.kendoui.datatable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Product;
import com.googlecode.wicket.jquery.ui.samples.data.provider.ProductDataProvider;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;
import com.googlecode.wicket.kendo.ui.datatable.column.CurrencyPropertyColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.IColumn;
import com.googlecode.wicket.kendo.ui.datatable.column.PropertyColumn;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class DefaultDataTablePage extends AbstractDataTablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultDataTablePage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Form //
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// DataTable //
		Options options = new Options();
		options.set("height", 430);
		options.set("pageable", "{ pageSizes: [ 25, 50, 100 ] }");
		// options.set("sortable", true); // already set, as provider IS-A ISortStateLocator
		options.set("groupable", true);
		options.set("columnMenu", true);
		options.set("reorderable", true);

		final DataTable<Product> table = new DataTable<Product>("datatable", newColumnList(), newDataProvider(), 25, options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onColumnReorder(AjaxRequestTarget target, int oldIndex, int newIndex, JSONObject column)
			{
				final String message = String.format("reordered: old-index=%d, new-index=%d, column=%s", oldIndex, newIndex, column.optString("field"));

				feedback.info(message);
				feedback.refresh(target);
			}
		};

		form.add(table);

		// Buttons //
		form.add(new AjaxButton("reload") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				table.reload(target, true);
			}
		});

		form.add(new AjaxButton("refresh") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				table.refresh(target);
			}
		});
	}

	private static IDataProvider<Product> newDataProvider()
	{
		return new ProductDataProvider();
	}

	private static List<IColumn> newColumnList()
	{
		List<IColumn> columns = new ArrayList<IColumn>();

		columns.add(new PropertyColumn("ID", "id", 50));
		columns.add(new PropertyColumn("Name", "name"));
		columns.add(new PropertyColumn("Description", "description"));
		columns.add(new CurrencyPropertyColumn("Price", "price", 70));
		// columns.add(new DatePropertyColumn("Date", "date"));
		columns.add(new PropertyColumn("Vendor", "vendor.name"));

		return columns;
	}
}

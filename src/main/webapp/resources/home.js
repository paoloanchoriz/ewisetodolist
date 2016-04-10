/**
 * 
 */
(function($, undefined) {
	$.loading().ajax(true);
	var homePage = $('#homePage');
	var todoList = $('#todoList');
	var pageContext = $('#pageContext').val();
	var homePageUrl = pageContext + '/todolist';
	var getHomePageRow = function(todoList) {
		var hiddenId = $('<input>').attr('type', 'hidden').addClass(
				'idContainer').val(todoList.id);
		var nameColumn = $('<td>').text(todoList.name).addClass('nameColumn');
		var descriptionColumn = $('<td>').text(todoList.description).addClass(
				'descriptionColumn');
		var editColumn = $('<td>')
				.html(
						'<a href="#" class="edit-link" title="Edit"><span class="glyphicon glyphicon-pencil"></span></a>');
		var deleteColumn = $('<td>')
				.html(
						'<a href="#" class="delete-link" title="Delete"><span class="glyphicon glyphicon-remove"></span></a>');
		var goColumn = $('<td>')
				.html(
						'<a href="#" class="go-link" title="Go"><span class="glyphicon glyphicon-arrow-right"></span></a>');
		var hiddenColumn = $('<td>').append(hiddenId);
		return $('<tr>').append(nameColumn).append(descriptionColumn).append(
				editColumn).append(deleteColumn).append(goColumn).append(
				hiddenColumn);
	}

	var todoListUrl = pageContext + '/todolist/<parent_id>';

	var homePageControl = new AjaxControl({
		url : homePageUrl,
		getRow : getHomePageRow,
		parent : homePage,
		unload : todoList,
		title : 'Create a Todo List',
		bindings : [ function() {
			var that = this;
			that.parent.on('click', '.go-link', function(e) {
				var parentRow = $(this).closest('tr');
				var id = parentRow.find('.idContainer').val();
				var newUrl = todoListUrl.replace('<parent_id>', id);
				var name = parentRow.find('.nameColumn').text();
				var description = parentRow.find('.descriptionColumn').text();
				that.unload.trigger('create', {
					url : newUrl + '/item',
					name : name,
					description : description,
					exportLink : newUrl + '/export'
				});
			});
		} ]
	});

	var isDoneContent = function(isDone) {
		return $('<input>').attr('type', 'checkbox').attr('checked', isDone)
				.addClass('done-trigger');
	}

	var getTodoListRow = function(todoItem) {
		var isDoneColumn = $('<td>').html(isDoneContent(todoItem.isDone))
				.addClass('isDoneColumn').css('padding', '5x');
		var nameColumn = $('<td>').text(todoItem.name).addClass('nameColumn');
		var descriptionColumn = $('<td>').text(todoItem.description).addClass(
				'descriptionColumn');
		var editColumn = $('<td>')
				.html(
						'<a href="#" class="edit-link" title="Edit"><span class="glyphicon glyphicon-pencil"></span></a>');
		var deleteColumn = $('<td>')
				.html(
						'<a href="#" class="delete-link" title="Delete"><span class="glyphicon glyphicon-remove"></span></a>');
		var hiddenId = $('<input>').attr('type', 'hidden').addClass(
				'idContainer').val(todoItem.id);
		var hiddenColumn = $('<td>').append(hiddenId);

		return $('<tr>').append(isDoneColumn).append(nameColumn).append(
				descriptionColumn).append(editColumn).append(deleteColumn)
				.append(hiddenColumn);
	}

	var todoListControl = new AjaxControl(
			{
				getRow : getTodoListRow,
				parent : todoList,
				unload : homePage,
				title : 'Add Todo Item',
				createFn : function(todoList) {
					this.parent.find('#todoListName').text(todoList.name);
					this.parent.find('#todoListDescription').text(
							todoList.description);
					this.parent.find('#export-link').attr('href',
							todoList.exportLink);
					this.url = todoList.url;
				},
				unloadFn : function() {
					this.parent.find('#todoListName').text('');
					this.parent.find('#todoListDescription').text('');
					this.parent.find('#export-link').attr('href', '#');
					this.url = '';
				},
				bindings : [
						function() {
							var that = this;
							that.parent.on('click', '.done-trigger',
									function() {
										var $_this = $(this);
										var isDone = $_this.is(':checked');
										var parentRow = $_this.closest('tr');
										isDone ? that.setDone(parentRow) : that
												.setUndo(parentRow);
									});
						}, function() {
							var that = this;
							that.parent.on('click', '#back-link', function(e) {
								e.preventDefault();
								that.unload.trigger('create');
								that.parent.trigger('destroy');
							});
						} ]
			});

	todoListControl.setDoneCall = function(parentRow, url, isDone) {
		var that = this;
		var id = parentRow.find('.idContainer').val();
		that.sendRequest({
			url : that.url + '/' + id + url,
			type : 'PUT',
			callback : function(content) {
				if (content !== 'success')
					return;
				parentRow.find('.isDoneColumn').find('[type="checkbox"]').attr(
						'checked', isDone);
			}
		});
	};

	todoListControl.setDone = function(parentRow) {
		this.setDoneCall(parentRow, '/setdone', true);
	};

	todoListControl.setUndo = function(parentRow) {
		this.setDoneCall(parentRow, '/setundone', false);
	};

	homePageControl.init();
	todoListControl.init();
	homePage.trigger('create');
})(jQuery, undefined);
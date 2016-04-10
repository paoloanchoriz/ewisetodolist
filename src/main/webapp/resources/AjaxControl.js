/**
 *  Author: Paolo Anchoriz
 */
(function($, undefined) {
	
	var handleError = function(errorMessages) {
		var errorContainer = $('#dialog-form #errorContainer');
		errorContainer.empty();
		for(var i in errorMessages) {
			var errorMessage = errorMessages[i];
			var div = $('<div>').addClass('alert').addClass('alert-danger');
			var span = $('<span>').addClass('glyphicon').addClass('glyphicon-exclamation-sign').attr('aria-hidden', true);
			div.append(span).append(errorMessage);
			errorContainer.append(div);
			errorContainer.show();
		}
	};
	
	function AjaxControl(options) {
		this.url = options.url;
		this.getRow = options.getRow;
		this.unload = options.unload;
		this.parent = options.parent;
		this.table = this.parent.find('#resultTable tbody');
		this.title = options.title;
		this.dialog = $('#dialog-form').dialog({
			autoOpen: false,
			height: 420,
			width: 400,
			modal: true,
			buttons: {},
			title: ''
		});
		this.emptyMessage = this.table.find('.emptyMessage');
		this.unloadFn = options.unloadFn;
		this.createFn = options.createFn;
		this.bindings = options.bindings;
	};
	
	AjaxControl.prototype.getUrl = function() {
		return this.url;
	};
	
	AjaxControl.prototype.sendRequest = function(options) {
		$('#dialog-form #errorContainer').empty();
		var ajaxOpt = {
			url: options.url,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				if(data.hasError)  {
					handleError(data.errorMessage);
					return;
				}
					
				if(options.callback) options.callback(data.content);
			}
		}
		
		if(options.data) ajaxOpt.data = JSON.stringify(options.data);
		ajaxOpt.type = options.type || 'GET'; 
		
		$.ajax(ajaxOpt);
	};
	
	AjaxControl.prototype.loadTable = function() {
		var that = this;
		that.sendRequest({
			url: that.getUrl(),
			callback: function(content) {
				that.unload.trigger('destroy');
				that.emptyMessage.hide();
				if(!content.length) {
					that.emptyMessage.show();
					return;
				}
				
				for(var item in content) {
					var todoList = content[item];
					var row = that.getRow(todoList);
					that.table.append(row);
				}
			}
		});
	};
	
	AjaxControl.prototype.getFormObject = function() {
		var todolistForm = $('#add');
		var name = todolistForm.find('#name').val();
		var description = todolistForm.find('#description').val();
		return { name: name, description: description }
	};
	
	AjaxControl.prototype.closeDialog = function() {
		$('#add')[0].reset();
		$('#dialog-form #errorContainer').empty();
		this.dialog.dialog('close');
	};
	
	AjaxControl.prototype.postRequest = function() {
		var that = this;
		that.sendRequest({
			url: that.getUrl(),
			type: 'POST',
			data: that.getFormObject(),
			callback: function(content) {
				var row = that.getRow(content);
				that.table.append(row);
				that.closeDialog();
				that.emptyMessage.hide();
			}
		});
	};
	
	AjaxControl.prototype.deleteItem = function(parentRow) {
		var that = this;
		var id = parentRow.find('.idContainer').val();
		that.sendRequest({
			url: that.getUrl() + '/' + id,
			type: 'DELETE',
			callback: function() {
				parentRow.empty().remove();
				var rows = that.table.find('tr');
				
				if(rows.length <= 1) {
					that.emptyMessage.show();
				}
			}
		});
	};
	
	AjaxControl.prototype.populateForm = function(parentRow) {
		var name = parentRow.find('.nameColumn').text();
		var description = parentRow.find('.descriptionColumn').text();
		var todolistForm = $('#add');
		todolistForm.find('#name').val(name);
		todolistForm.find('#description').val(description);
	};
	
	AjaxControl.prototype.edit = function(parentRow) {
		var that = this;
		var id = parentRow.find('.idContainer').val();
		that.sendRequest({
			url: that.getUrl() + '/' + id,
			type: 'PUT',
			data: that.getFormObject(),
			callback: function(content) {
				parentRow.find('.nameColumn').text(content.name);
				parentRow.find('.descriptionColumn').text(content.description);
				that.closeDialog();
			}
		});
	};
	
	AjaxControl.prototype.init = function() {
		var that = this;
		var parent = that.parent;
		
		parent.on('click', '#add-link', function(e) {
			e.preventDefault();
			that.dialog.dialog('option', 'buttons', {
				'Add': function() {
					that.postRequest();
				},
				Cancel: function() {
					that.closeDialog();
				}
			}).dialog('option', 'title', that.title).dialog('open');
		});
		
		parent.on('click', '.edit-link', function(e) {
			e.preventDefault();
			var parentRow = $(this).closest('tr');
			that.dialog.dialog('option', 'buttons', {
				'Update': function() {
					that.edit(parentRow);
				},
				Cancel: function() {
					that.closeDialog();
				}
			}).dialog('option', 'title', that.title).dialog('open');
			that.populateForm(parentRow);
		});
		
		parent.on('click', '.delete-link', function(e) {
			e.preventDefault();
			var parentRow = $(this).closest('tr');
			that.deleteItem(parentRow);
		});
		
		parent.on('destroy', function(e) {
			var $_this = $(this);
			$_this.hide();
			
			that.table.find('tr')
				.not(':first')
				.not('#emptyMessage')
				.empty().remove();
			
			if(that.unloadFn)
				that.unloadFn();
		});
		
		parent.on('create', function(e, obj) {
			if(that.createFn)
				that.createFn(obj);
			$(this).show();
			that.loadTable();
		});
		
		if(that.bindings) 
			for(var fn in that.bindings) {
				that.bindings[fn].apply(that);
			}
		
		$('form').submit(function(e) {
			e.preventDefault();
			$(this).closest('.ui-dialog')
				.find('.ui-dialog-buttonpane button')
				.not(':contains("Cancel")')
				.trigger('click');
		});
	};
	
	window.AjaxControl = AjaxControl;
})(jQuery, undefined);
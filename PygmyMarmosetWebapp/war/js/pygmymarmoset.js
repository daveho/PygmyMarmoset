// Common PygmyMarmoset JavaScript functions/data.
// All are accessed via the pm. prefix.

pm = {
	dateTimeOptions: {
		dateFormat: 'yy-mm-dd',
		timeFormat: 'HH:mm:ss',
		hour: 23,
		minute: 59,
		second: 59
	},

	autocompleteOn: function(elementId, suggestionUri) {
		$(elementId).autocomplete({
			source: function(req, resp) {
				$.post(
						// URL
						suggestionUri,
						// Data to send
						{ term: req.term },
						// Success function
						function(data) {
							resp(data);
						},
						// Data type expected from server
						'json'
				);
			}
		});
	}
};

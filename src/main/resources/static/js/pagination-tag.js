function clearPages() {
				$("#pg_tag-table").empty();
				$("#tagtable > tbody > tr").show();
				$(".no-record").remove();
				$("#tagtable > tbody > tr").removeData("page");
		        return true;
		    }
			
			function _createTagPagination() {
				clearPages();
		        var navBar = _getNavBar();
		        $('.select-button-tag').after(navBar);
		    }

		    function _getNavBar() {
		        var nav = $("#pg_tag-table");	        
		        navbar = nav;
		        setPages(1);
		        return nav;
		    }
		    
		    function setPages(pageNum) {
		        var retVal = false;
		            var pageLimit = 5;
		            var rows = $("#tagtable > tbody > tr");
		            var rowsLength = rows.length;
		            if(rowsLength != 0){

			            var pages;
			            var totalPages = rowsLength / pageLimit;
			            pages = totalPages;
			            var numArr = totalPages.toString().split(".");
			            if (numArr.length == 2)
			                pages = parseInt(numArr[0]) + 1; 
			            if (pages == 0)
			                pages = 1;
			            
			            if(pages != 1){
			            	var elementID = "tagtable";

			                _setPage(elementID, 1, "<<");
			                _setPage(elementID, "P-1", "<");
			                var startRow = 0; var endRow = pageLimit;
			                var page;
			                for (var i = 1; i <= pages; i++) {
			                    var pgrows;
			                    pgrows = rows.slice(startRow, endRow);
			                    page = i;
			                    $(pgrows).removeData("page");
			                    $(pgrows).data("page", page);
			                    startRow = endRow;
			                    endRow = (parseInt(endRow) + parseInt(pageLimit));
			                }
			                _setPage(elementID, "P+1", ">");
			                _setPage(elementID, page, ">>");
			                gTotalPages = page;
			                if (pageNum !== undefined)
			                	_activePage(pageNum);
			                retVal = true;
			            }
		            }else{
		            	var noRecordRow = $('<tr></tr>').addClass("no-record");
		            	var data = $('<td colspan="4" align="center"></td>').text('用語がありません').appendTo(noRecordRow);
		            	var table = $("#tagtable > tbody");
		            	noRecordRow.appendTo(table);
		            }
		            
		      
		        return retVal;
		    }
		    
		    function _setPage(parentID, pageNum, pageText) {
		        var pageLink = $('<button>', { href: '#', text: (pageText), "data-page": (pageNum), class:'form-control nav-button' }).appendTo(navbar);
		    }
		    
		    function _activePage(pageNum) {
		        var num = pageNum * 1;
		        activePage = num;
		        $("#tagtable > tbody > tr").each(function () {
		            if ($(this).data("page") == num) {
		                $(this).show();
		            }
		            else {
		                $(this).hide();
		            }
		        });
		        _selectActivePage();
		    }
		    
		    function _selectActivePage() {
		        var currentPage = activePage;
		        $(navbar).find("a").each(function () {
		            var linkText = $(this).text();
		            if ($(this).data("page") == currentPage &&
		                (linkText != "<<" && linkText != ">>")) {
		                $(this).attr("class", "active");
		                $(this).siblings().attr("class", "");
		            }
		        });
		    }
		    
		    function onPageClick(event) {
		        event.preventDefault();
		        var pageNum = $(event.target).data("page");
		        if (pageNum == "P-1") {
		            pageNum = activePage - 1;
		            if (pageNum < 1)
		                pageNum = 1;
		        }
		        else if (pageNum == "P+1") {
		            pageNum = activePage + 1;
		            if (pageNum > gTotalPages)
		                pageNum = gTotalPages;
		        }
		        _activePage(pageNum);
		    }
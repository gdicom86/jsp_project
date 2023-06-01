
async function postCommentToServer(cmtData) {
	try {
		const url = "/cmt/post";
		const config = {
			method: 'post',
			headers: {
				'content-Type': 'application/json; charset=utf-8'
			},
			body: JSON.stringify(cmtData)
		};
		const res = await fetch(url, config);
		const result = await res.text(); 
		return result;
	} catch(error) {
		console.log(error);
	}
}


document.getElementById('cmtAddBtn').addEventListener('click', () => {
	const cmtText = document.getElementById('cmtText').value;
	console.log(cmtText);
	if(cmtText == null || cmtText == "") {
		alert('댓글을 입력해주세요');
		return false;
	} else {
		let cmtData = {
			bno :  bnoVal,
			writer : document.getElementById('cmtWriter').value,
			content : cmtText		
		};
		postCommentToServer(cmtData).then(result => {
			if(result > 0) {
				alert('댓글 등록성공!!');
				document.getElementById('cmtText').value = "";
			}
		
			printCommentList(cmtData.bno);
		});
	}
});

async function getCommentListFromServer(bno) {
	try{
		const res = await fetch('/cmt/list/'+ bno); 
		const cmtList = await res.json();
		return cmtList;
	} catch(error) {
		console.log(error);
	}	
}


function printCommentList(bno) {
	getCommentListFromServer(bno).then(result => { 
		console.log(result);
		if(result.length > 0) { 
			spreadCommentList(result);
		} else {
			let div = document.getElementById('accordionExample');
			div.innerText = "Not exist comment";
		}
	})
}

function spreadCommentList(result) { 
	console.log(result);
	let div = document.getElementById('accordionExample');
	div.innerHTML = '';
	for(let i = 0 ; i < result.length; i++) {
		let html = `<div class="accordion-item">`;
		html += `<h2 class="accordion-header" id="heading${i}">`;
		html += ` <button class="accordion-button" type="button" data-bs-toggle="collapse" `;
		html += `data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
		html += `${result[i].cno}, ${result[i].writer}  </button></h2>`;
		html += `<div id="collapse${i}" class="accordion-collapse collapse show"`;
		html += `aria-labelledby="heading${i}" data-bs-parent="#accordionExample">`;
		html += `<div class="accordion-body">`;
		html += `<button type="button" data-cno="${result[i].cno}" data-writer="${result[i].writer}" class="btn btn-sm btn-outline-warning cmtModBtn">%</button>`;
		html += `<button type="button" data-cno="${result[i].cno}" class="btn btn-sm btn-outline-danger cmtDelBtn">X</button>`;
		html += `<input type="text" class="form-control" id="cmtText1" value="${result[i].content}">`;
		html += `${result[i].reg_date}`;
		html += `</div></div></div>`;
		div.innerHTML += html; 
	}

}

async function removeCommentFromServer(cnoVal) {
	try {
		const url = '/cmt/remove?cnoVal=' + cnoVal;
		const config = {
			method: 'post'
		}
		const res = await fetch(url, config);
		const result = await res.text(); 
		return result;
	} catch(error) {
		console.log(error);
	}
}

async function updateCommentFromServer(cnoVal, cmtText1, writer) {
	try {
		const url = "/cmt/modify";
		const config = {
			method: "post",
			headers: {
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body: JSON.stringify({cno: cnoVal, content: cmtText1, writer: writer})
		}
		const res = await fetch(url, config);
		const result = await res.text(); 
		return result;		
	} catch(error) {
		console.log(error);
	}
}

document.addEventListener('click', (e) => { 
	if(e.target.classList.contains('cmtModBtn')) {
		let cnoVal = e.target.dataset.cno;
		console.log(cnoVal);
		
		let div = e.target.closest('div'); 
		let cmtText1 = div.querySelector('#cmtText1').value;
		let writer = e.target.dataset.writer;
	
		updateCommentFromServer(cnoVal, cmtText1, writer).then(result => {
			console.log(result);
			if(result > 0) {
				alert('댓글 수정 성공!');
				console.log(result);
				printCommentList(bnoVal);
			} else {
				alert('댓글 수정 불가!');
			}
		})
	}
	if(e.target.classList.contains('cmtDelBtn')) {
		let cnoVal = e.target.dataset.cno;
		console.log(cnoVal);
		removeCommentFromServer(cnoVal).then(result => {
			if(result > 0) {
				alert('댓글 삭제 완료!');
				printCommentList(bnoVal);
				console.log(bnoVal);
			}
		})
	}
	
})

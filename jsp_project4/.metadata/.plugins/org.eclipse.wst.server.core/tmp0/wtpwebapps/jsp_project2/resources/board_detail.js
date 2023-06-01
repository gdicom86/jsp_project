// 화면에서 등록한 댓글 내용을 컨트롤러로 전송 -> db에 저장
//async 비동기 통신
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
		const result = await res.text(); //isOk - controller에서 보내는 
		return result;
	} catch(error) {
		console.log(error);
	}
}
//미리 보낼 데이터를 만들어서 함수로 전달

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
			//댓글 표시 
			printCommentList(cmtData.bno);
		});
	}
});

//댓글 가져오기
//컨트롤러에 가서 bno에 해당하는 댓글 리스트 달라고 요청 
async function getCommentListFromServer(bno) {
	try{
		//data가 controller로 이동한 후 컨트롤러 처리를 하고
		//응답데이터를 가져와서 res에 저장
		const res = await fetch('/cmt/list/'+ bno); //cmt/list/1
		const cmtList = await res.json(); //댓글 객체가 리턴
		return cmtList;
	} catch(error) {
		console.log(error);
	}	
}

//통신 완료 후 가져온 리스트를 콘솔에 찍어본 작업
function printCommentList(bno) {
	getCommentListFromServer(bno).then(result => { //result ctmList
		console.log(result);
		if(result.length > 0) { //등록된 댓글이 있음
			spreadCommentList(result);
		} else {
			let div = document.getElementById('accordionExample');
			div.innerText = "Not exist comment";
		}
	})
}

function spreadCommentList(result) { //댓글 List
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
		div.innerHTML += html; //누적해서 담기
	}

}

async function removeCommentFromServer(cnoVal) {
	try {
		const url = '/cmt/remove?cnoVal=' + cnoVal;
		const config = {
			method: 'post'
		}
		const res = await fetch(url, config);
		const result = await res.text(); //isOk
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
		const result = await res.text(); // isOk
		return result;		
	} catch(error) {
		console.log(error);
	}
}

document.addEventListener('click', (e) => { // e 내가 클릭한 객체
	if(e.target.classList.contains('cmtModBtn')) {
		//수정작업
		let cnoVal = e.target.dataset.cno;
		console.log(cnoVal);
		//기존 위치에서 값을 읽어들여 내용을 DB에 저장 한 후 변경
		
		//현재 수정하고자 하는 값 input box의 value를 찾기위한 작업
		let div = e.target.closest('div'); //closest -> 타겟을 기준으로 가장 가까운 값 찾기		
		let cmtText1 = div.querySelector('#cmtText1').value;
		let writer = e.target.dataset.writer;
		
		//비동기 통신
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
		//삭제작업
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

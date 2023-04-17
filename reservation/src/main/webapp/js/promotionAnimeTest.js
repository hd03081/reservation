var image_ul = document.querySelector(".slideshow");

	window.onload = function() {
		var imgCnt = 0;
		/* Animation: sliding setting */
		image_ul.querySelectorAll("li").forEach(()=> { //프로모션단의 모든 이미지들을 돌면서 그 갯수를 imgCnt에 대입
			imgCnt ++;
		});
		image_ul.style.width = (image_ul.offsetWidth * imgCnt) + "px"; //이미지 표시박스를 모든 이미지의 길이를 합친것만큼으로 늘림
		
		console.log(imgCnt); //프로모션 상품 갯수 확인
		console.log(image_ul.style.width); //늘어난 width 값 확인
		slideShow(imgCnt); //애니메이션 함수 실행
	}
	
		/* Animation: sliding */
	function slideShow(imgCnt) {
		var curIndex = 0; //현재위치 인덱싱
		
		setInterval( () => {
			image_ul.style.transition = "transform 2s ease-out"; //2초 간격 ease-out transform 
			image_ul.style.transform = "translate3d(-" + 414*(curIndex+1)+"px, 0px, 0px)"; //3d 환경에서 x축으로 마이너스 좌표이동, 늘려진 전체넓이 기준으로 각 프로모션 아이템을 가리키도록 설정
			curIndex++; //인덱싱값 ++
			
			//console.log(curIndex);
			if( curIndex === imgCnt-1 ) {
				curIndex = -1; //인덱싱값이 총이미지 숫자보다 낮아져버리면, 즉 프로모션 끝까지 갔을 경우에는 curIndex를 -1로 초기화
			}
		},2000);	//인터발 2000
	}
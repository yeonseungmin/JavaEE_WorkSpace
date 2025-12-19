
/*상품 이미지를 선택하면, 해당 상품이미지를 화면에 미리보기 기능 구현을 위한 클래스 정의*/
class PreviewImg{
	constructor(container, file, src, width, height){
		this.container=container;
		this.file=file;		//자바스크립트 배열내에서 이 파일이 몇번째에 들어있는지 조사하기위해 
		this.src=src;
		this.width=width;
		this.height=height;
		
		this.wrapper=document.createElement("div");
		this.header=document.createElement("div");
		this.img=document.createElement("img");
		this.img.src=this.src;
		
		//style
		this.img.style.width=this.width+"px";
		this.img.style.height=this.height+"px";
		
		this.wrapper.style.width=(this.width+10)+"px";
		this.wrapper.style.height=(this.height+30)+"px";
		this.wrapper.style.display="inline-block"; //너비,높이 등의 블락요소의 특징을 유지하면서, 다른 요소와 수평선상에 공존하게하려
		this.wrapper.style.margin=5+"px";
		this.wrapper.style.border="1px solid red";
		this.wrapper.style.borderRadius="5px";
		this.wrapper.style.textAlign="center";
		
		this.header.innerHTML="<a href='#'>X</a>";
		this.header.style.textAlign="right";
		
		//조립 
		this.wrapper.appendChild(this.header);
		this.wrapper.appendChild(this.img);
		this.container.appendChild(this.wrapper);
		
		//X자에 이벤트 연결
		this.header.addEventListener("click", (e)=>{
			//링크를 누를때 마다 스크롤이 자꾸 원상태로 돌아오는 현상의 이유 > <a>태그를 사용자가 클릭하면 기본적으로 y축을 0으로 위치시키는 특징
			//아래의 preventDefault() 가 기본기능 방지
			e.preventDefault();
			this.remove();
		});
	}	
	//삭제 메서드 추가
	remove(){
		//product-preview 컨테이너 안에있는 나의(this.wrapper)를 지운다.	> 화면에서 제거
		this.container.removeChild(this.wrapper);
		//화면에서 제거되었다고 하여 안심하면 안된다. 제거된 최종결과를 서버로 전송할것이므로, 원본 배열도 함께 제거해줘야한다. > 원본 복사본 배열에서 제거
		//selectedFile.splice(지울 갯수,유저가 선택한 배열내에서의 인덱스 조사)
		//인덱스 번호 조사 배열.indexOf("이름");		.. 최상단에 파라미터로 file을 받아 인덱스를 삽입해놓음
		selectedFile.splice(1,selectedFile.indexOf(this.file));
		
		
		
	}
}
class Box{
    // js의 2015년 버전인 ES6 부터는 클래스가 지원되므로, 다이어리를 이루는 셀을 클래스로 정의하여 재사용하자
            constructor(container,x,y,width,height,bg,msg){
                this.container=container;
                this.div=document.createElement("div");
                this.x =x;
                this.y = y;
                this.width = width;
                this.height =height;
                this.bg =bg;
                this.msg=msg;
                this.dd; //박스가 보유할 날짜 (printNum 함수로 이중 반복문을 돌때 날짜를 주입받아야함)

                //css 적용
                this.div.style.position="absolute";
                this.div.style.left=this.x+"px";
                this.div.style.top=this.y+"px";

                this.div.style.width=this.width+"px";
                this.div.style.height=this.height+"px";
                this.div.style.backgroundColor=this.bg;
                this.div.style.borderRadius="3px";
                
                //메시지 추가
                this.div.innerText=this.msg;

                this.div.style.border="1px solid #cccccc";
                //화면에 부착
                this.container.appendChild(this.div);

                //마우스 오버 이벤트 연결(화살표 함수의 this는 상위 스코프를 가리키므로, 현재 매서드에서의 상위 스코프는 box라는 객체이다.)
                this.div.addEventListener("mouseover",()=>{
                    this.div.style.backgroundColor="";
                });
                this.div.addEventListener("mouseout",()=>{
                    this.div.style.backgroundColor="yellow";
                });
                this.div.addEventListener("click",()=>{
                    alert(currentDate.getFullYear()+"年"+(currentDate.getMonth()+1)+"月"+this.dd+"日");
                    
                });

            }
            //텍스트 넣기
            setMsg(msg){
                this.div.innerText = msg;
            }
            //날짜 대입받기
            setDate(dd){
                this.dd=dd;
            }
            
        }
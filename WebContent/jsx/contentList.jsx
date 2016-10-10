/**
 * Created by apple on 16/9/19.
 */
var MyList=React.createClass({
    render:function(){
        var flist=this.props.data.map(function(content){
            var modifyLink="/content/updateH?id="+content.contentId;
            return(
                <tr>
                    <td>{content.contentId}</td>
                    <td>{content.title}</td>
                    <td>{content.kindName}</td>
                    <td><input type="checkbox" name="" id="" checked={content.isTop} disabled="disabled" /></td>
                    <td><input type="checkbox" name="" id="" checked={content.isRecommend} disabled="disabled" /></td>
                    <td><input type="checkbox" name="" id="" checked={content.isSlide} disabled="disabled" /></td>
                    <td><a href={modifyLink}>修改</a> <a href="javascript:;" className="delContent" alt={content.contentId}>删除</a></td>
                </tr>
            )
        })
        return(
            <tbody>
            {flist}
            </tbody>
        )
    }
})
<template>
  <a-form :form="form">
    <a-form-item
      label="一级标签" >
      <a-tree-select
        style="width:100%"
        :dropdownStyle="{ maxHeight: '200px', overflow: 'auto' }"
        :treeData="treeData"
        v-model="model.parentId"
        @change="onChangeData"
        placeholder="请选择一级标签">
      </a-tree-select>
    </a-form-item>
    <a-form-item label="数值类型">
      <a-radio-group @change="onChangeMenuType" v-decorator="['menuType',{'initialValue':0}]">
        <a-radio :value="0">范围</a-radio>
        <a-radio :value="1">数值</a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item label="范围" v-show="localMenuType==0">
        <a-input-number size="small" :min="1" :max="999999999"  v-decorator="['range1',{'initialValue':1}]" />
        ~
        <a-input-number size="small" :min="1" :max="999999999" v-decorator="['range2',{'initialValue':2}]" />
    </a-form-item>
    <a-form-item
       v-show="localMenuType==1"
      label="数值">
      <a-input placeholder="请输入数值" v-decorator="['value', {} ]" />
    </a-form-item>
  </a-form>
</template>

<script>
  import { getAction,putAction,postAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "LabelUserSubTagModel",
    props:["partInfo"],
    data () {
      return {
            form: this.$form.createForm(this),
            localMenuType: 0,
            treeData:[],
            model: {},
      }
    },
    created(){
      this.loadTree()
    },
    methods: {
        addTag() {
          console.log(this.partInfo)
        },
        onChangeMenuType(e) {
          this.localMenuType=e.target.value
        },
        loadTree(){
          var that = this;
          getAction("/label/labelUserTag/queryTreeList").then((res)=>{
            if(res.success){
              that.treeData = [];
              let treeList = res.result.treeList
              for(let a=0;a<treeList.length;a++){
                let temp = treeList[a];
                temp.isLeaf = temp.leaf;
                that.treeData.push(temp);
              }
            }
          });
        },
        onChangeData(a,info) {
            if (info) {
                this.model.title=info[0]
            }
        },
        getData() {
            let  formData
            this.form.validateFields((err, values) => {
              if (!err) {
                formData = Object.assign(this.model, values);
              }
            })
            if (formData){
              var res = formData.title+"(";
              if(formData.menuType===0) {
                res=res+formData.range1+"~"+formData.range2;
              }else if (formData.menuType===1){
                res=res+formData.value;
              }
              res=res+")";
              return res;
            }
            return null;
        },
    }
  }
</script>

<style scoped>

</style>
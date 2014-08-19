<tr>
	<td valign="top" align="right" colspan="2">
		<label for="${parameters.id?html}" class="checkboxLabel">${parameters.label?html}</label>	
		<input type="checkbox" name="${parameters.name?html}" value="${parameters.fieldValue?html}" id="${parameters.id?html}" />
		<input type="hidden" id="__checkbox_${parameters.id?html}" name="__checkbox_${parameters.name?html}" value="${parameters.fieldValue?html}" />
	</td>
</tr>

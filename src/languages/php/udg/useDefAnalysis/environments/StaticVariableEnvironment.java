package languages.php.udg.useDefAnalysis.environments;

import java.util.Collection;
import java.util.LinkedList;

import udg.ASTProvider;
import udg.useDefAnalysis.environments.UseDefEnvironment;
import udg.useDefGraph.UseOrDef;

public class StaticVariableEnvironment extends UseDefEnvironment
{
	private Collection<String> defSymbols = new LinkedList<String>();
	private Collection<String> useSymbols = new LinkedList<String>();
	
	public void addChildSymbols( LinkedList<String> childSymbols, ASTProvider child)
	{
		this.symbols.addAll( childSymbols);

		// the left child is a StringExpression containing the variable name
		if( isDef( child))
			defSymbols.add( child.getEscapedCodeStr());
		// the right side may contain symbols that are USE'd to determine the
		// default value for the static variable
		if( isUse( child))
			useSymbols.addAll( childSymbols);
	}

	public Collection<UseOrDef> useOrDefsFromSymbols(ASTProvider child)
	{
		LinkedList<UseOrDef> retval = new LinkedList<UseOrDef>();

		if( isDef( child))
			retval.addAll(createDefsForAllSymbols(defSymbols));

		if( isUse( child))
			retval.addAll(createUsesForAllSymbols(useSymbols));

		return retval;
	}
	
	@Override
	public boolean isDef( ASTProvider child)
	{
		int childNum = child.getChildNumber();
		return 0 == childNum ? true : false;
	}
	
	@Override
	public boolean isUse( ASTProvider child)
	{
		int childNum = child.getChildNumber();
		return 1 == childNum ? true : false;
	}
}
package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_LukaWickedDevil extends Card {
    
    public SIGNI_K2_LukaWickedDevil()
    {
        setImageSets("WXDi-P03-085");
        
        setOriginalName("凶魔　ルカ");
        setAltNames("キョウマルカ Kyouma Ruka");
        setDescription("jp",
                "=R あなたの＜悪魔＞のシグニ１体の上に置く\n\n" +
                "@E %K %X：黒ではない対戦相手のパワー3000以下のすべてのシグニをバニッシュする。"
        );
        
        setName("en", "Luca, Doomed Evil");
        setDescription("en",
                "=R Place on top of a <<Demon>> SIGNI on your field. \n" +
                "@E %K %X: Vanish all non-black SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Luka, Wicked Devil");
        setDescription("en_fan",
                "=R Put on 1 of your <<Devil>> SIGNI\n\n" +
                "@E %K %X: Banish all of your opponent's non-black SIGNI with power 3000 or less."
        );
        
		setName("zh_simplified", "凶魔 路加");
        setDescription("zh_simplified", 
                "=R在你的<<悪魔>>精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E %K%X:不是黑色的对战对手的力量3000以下的全部的精灵破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.DEVIL));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            banish(new TargetFilter().OP().SIGNI().not(new TargetFilter().withColor(CardColor.BLACK)).withPower(0,3000).getExportedData());
        }
    }
}

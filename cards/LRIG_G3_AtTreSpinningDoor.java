package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.EnerCost;

import java.util.List;

public final class LRIG_G3_AtTreSpinningDoor extends Card {
    
    public LRIG_G3_AtTreSpinningDoor()
    {
        setImageSets("WXDi-D01-004");
        
        setOriginalName("紡ぎし扉　アト＝トレ");
        setAltNames("ツムギシトビラアトトレ Tsumugishi Tobira Ato Tore");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^A $T1 %G0：それぞれ異なるクラスを持つあなたのシグニ３体を対象とし、ターン終了時まで、それらのパワーを＋３０００する。\n" +
                "@E：[[エナチャージ２]]をする。この方法でエナゾーンに置かれたカードが共通するクラスを持たない場合、追加で[[エナチャージ１]]をする。"
        );
        
        setName("en", "At =Tre=, the Opened Gate");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 %G0: Target three SIGNI on your field with different classes get +3000 power until end of turn.\n" +
                "@E: [[Ener Charge 2]]. If the cards put into your ener zone this way do not share a class, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "At-Tre, Spinning Door");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 %G0: Target 3 of your SIGNI that do not share a common class, and until end of turn, they get +3000 power.\n" +
                "@E: [[Ener Charge 2]]. If the cards put into the ener zone this way do not share a common class, additionally [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "纺转扉 亚特=TRE");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^A$T1 %G0:持有不同类别的你的精灵3只作为对象，直到回合结束时为止，这些的力量+3000。\n" +
                "@E :[[能量填充2]]。这个方法放置到能量区的牌不持有共通类别的场合，追加[[能量填充1]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) &&
                   CardAbilities.getUniqueSIGNIClasses(getSIGNIOnField(getOwner())).size() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(3, new TargetFilter(TargetHint.PLUS).own().SIGNI(), this::onActionEffTargetCond);
            gainPower(data, 3000, ChronoDuration.turnEnd());
        }
        private boolean onActionEffTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 3 && CardAbilities.getUniqueSIGNIClasses(new DataTable<>(listPickedCards)).size() >= 3;
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = enerCharge(2);
            if(data.size() == 2 && !data.get(0).getIndexedInstance().getSIGNIClass().matches(data.get(1).getIndexedInstance().getSIGNIClass()))
            {
                enerCharge(1);
            }
        }
    }
}

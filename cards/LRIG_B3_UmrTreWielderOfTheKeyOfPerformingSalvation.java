package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.FieldZone;

public final class LRIG_B3_UmrTreWielderOfTheKeyOfPerformingSalvation extends Card {
    
    public LRIG_B3_UmrTreWielderOfTheKeyOfPerformingSalvation()
    {
        setImageSets("WXDi-P00-015");
        
        setOriginalName("奏救の鍵主　ウムル＝トレ");
        setAltNames("ソウキュウノカギヌシウムルトレ Soukyuu no Kaginushi Umuru Tore");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^A $T1 %B0：あなたのシグニ１体を対象とし、それを他のシグニゾーン１つに配置する。\n" +
                "@E：ターン終了時まで、対戦相手のシグニゾーン１つを消す。"
        );
        
        setName("en", "Umr =Tre=, Key to Salvation");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 %B0: Move target SIGNI on your field to a different SIGNI zone.\n" +
                "@E: Erase one of your opponent's SIGNI zones until end of turn."
        );
        
        setName("en_fan", "Umr-Tre, Wielder of the Key of Performing Salvation");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^A $T1 %B0: Target 1 of your SIGNI, and move it onto 1 of your other SIGNI zones.\n" +
                "@E: Until end of turn, delete 1 of your opponent's SIGNI zones."
        );
        
		setName("zh_simplified", "奏救的键主 乌姆尔=TRE");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^A$T1 %B0:你的精灵1只作为对象，将其往其他的精灵区1个配置。\n" +
                "@E :直到回合结束时为止，对战对手的精灵区1个消除。（那里全部的牌放置到废弃区。玩家不能在那里把精灵配置）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onActionEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI()).get();
            
            if(cardIndex != null)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.MOVE).own().SIGNI().unoccupied()).get();
                if(fieldZone != null) moveToZone(cardIndex, fieldZone.getZoneLocation());
            }
        }
        
        private void onEnterEff()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.DELETE).OP().SIGNI()).get();
            deleteZone(fieldZone, ChronoDuration.turnEnd());
        }
    }
}

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
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_DJLOVIT3rdVerse extends Card {
    
    public LRIG_R3_DJLOVIT3rdVerse()
    {
        setImageSets("WXDi-P02-016", "SPDi07-05","SPDi08-05");
        
        setOriginalName("DJ.LOVIT-3rdVerse");
        setAltNames("ディージェーラビットサードヴァース Dii Jee Rabitto Saado Vaasu");
        setDescription("jp",
                "=T ＜Card Jockey＞\n" +
                "^A #D：対戦相手のセンタールリグがレベル３以上の場合、対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %R0：ターン終了時まで、このルリグは@>@A @[アップ状態のレベル２のルリグ１体をダウンする]@：このルリグをアップする。@@を得る。"
        );
        
        setName("en", "DJ LOVIT - 3rd Verse");
        setDescription("en",
                "=T <<Card Jockey>>\n" +
                "^A #D: If your opponent's center LRIG is level three or more, put target card from that player's Ener Zone into their trash.\n" +
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@A $G1 %R0: This LRIG gains@>@A @[Down an upped level two LRIG]@: Up this LRIG.@@until end of turn."
        );
        
        setName("en_fan", "DJ.LOVIT - 3rd Verse");
        setDescription("en_fan",
                "=T <<Card Jockey>>\n" +
                "^A #D: If your opponent's center LRIG is level 3 or higher, target 1 card from your opponent's ener zone, and put it into the trash.\n" +
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@A $G1 %R0: Until end of turn, this LRIG gains:" +
                "@>@A @[Down 1 of your upped level 2 LRIGs]@: Up this LRIG."
        );
        
		setName("zh_simplified", "DJ.LOVIT-3rdVerse");
        setDescription("zh_simplified", 
                "=T<<Card:Jockey>>\n" +
                "^A#D:对战对手的核心分身在等级3以上的场合，从对战对手的能量区把1张牌作为对象，将其放置到废弃区。\n" +
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %R0:直到回合结束时为止，这只分身得到\n" +
                "@>@A 竖直状态的等级2的分身1只#D:这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act1 = registerActionAbility(new DownCost(), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onActionEff1Cond()
        {
            return isLRIGTeam(CardLRIGTeam.CARD_JOCKEY) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            if(getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
                trash(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onActionEff2()
        {
            ActionAbility attachedAction = new ActionAbility(new DownCost(1, new TargetFilter().own().anyLRIG().withLevel(2).upped()), this::onAttachedActionEff);
            attachAbility(getCardIndex(), attachedAction, ChronoDuration.turnEnd());
        }
        private void onAttachedActionEff()
        {
            up();
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExcludeCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_CodeAncientsOsiris extends Card {
    
    public SIGNI_W3_CodeAncientsOsiris()
    {
        setImageSets("WXDi-P06-032");
        
        setOriginalName("コードアンシエンツ　オシリス");
        setAltNames("コードアンシエンツオシリス Koodo Anshientsu Oshirisu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、レベルがあなたの場にいる白のルリグの数以下の対戦相手のシグニ１体を対象とし、%W %Wを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@A %W：次の対戦相手のターン終了時まで、このシグニは@>@C：対戦相手のターンの間、【シャドウ】を得る。@@を得る。\n\n" +
                "@A %W %W %X %X %X @[トラッシュにあるこのカードをゲームから除外する]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Osiris, Code: Ancients");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may pay %W %W. If you do, return target SIGNI on your opponent's field with a level less than or equal to the number of white LRIG on your field to its owner's hand.\n" +
                "@A %W: This SIGNI gains@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@until the end of your opponent's next end phase.\n\n" +
                "@A %W %W %X %X %X @[Remove this card in your trash from the game]@: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Code Ancients Osiris");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with level lower than or equal to the number of white LRIGs on your field, and you may pay %W %W. If you do, return it to their hand.\n" +
                "@A %W: Until the end of your opponent's next turn, this SIGNI gains:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow]].@@\n" +
                "@A %W %W %X %X %X @[Exclude this card in your trash from the game]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "古神代号 奥西里斯");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，等级在你的场上的白色的分身的数量以下的对战对手的精灵1只作为对象，可以支付%W %W。这样做的场合，将其返回手牌。\n" +
                "@A %W:直到下一个对战对手的回合结束时为止，这只精灵得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影]]。@@\n" +
                "@A %W %W%X %X %X:废弃区的这张牌从游戏除外从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onActionEff1);
            
            ActionAbility act2 = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(3)),
                new ExcludeCost()
            ), this::onActionEff2);
            act2.setActiveLocation(CardLocation.TRASH);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0, new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount())).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 2)))
            {
                addToHand(target);
            }
        }
        
        private void onActionEff1()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}

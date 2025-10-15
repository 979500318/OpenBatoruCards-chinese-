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
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_CodePirulukXi extends Card {
    
    public LRIG_B3_CodePirulukXi()
    {
        setImageSets("WXDi-P06-012", "WXDi-P06-012U", "WXDi-D09-P04");
        
        setOriginalName("コード・ピルルク・ｘｉ");
        setAltNames("コードピルルクサイ Koodo Piruruku Sai");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札が対戦相手より３枚以上多い場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。６枚以上多い場合、代わりにターン終了時まで、それのパワーを－6000する。\n" +
                "@E：カードを２枚引く。\n" +
                "@A $G1 %B0：対戦相手のセンタールリグのレベル以下の数字１つを宣言する。対戦相手の手札を見て、#Gを持たず宣言した数字と同じレベルを持つすべてのシグニを捨てさせる。"
        );
        
        setName("en", "Code Piruluk xi");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have three or more cards in your hand than your opponent, target SIGNI on your opponent's field gets --3000 power until end of turn. If you have six or more cards in your hand than your opponent, it gets --6000 power until end of turn instead.\n" +
                "@E: Draw two cards.\n" +
                "@A $G1 %B0: Declare a number less than or equal to the level of your opponent's Center LRIG. Look at your opponent's hand and your opponent discards all SIGNI without a #G that are the same level as the declared number."
        );
        
        setName("en_fan", "Code Piruluk xi");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are at least 3 more cards in your hand than in your opponent's, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If there are at least 6 more cards in your hand than in your opponent's, instead until end of turn, it gets --6000 power.\n" +
                "@E: Draw 2 cards.\n" +
                "@A $G1 %B0: Declare 1 number equal to or less than your opponent's center LRIG's level. Look at your opponent's hand, and discard all SIGNI from it without #G @[Guard]@ whose level is equal to the declared number."
        );
        
		setName("zh_simplified", "代号·皮璐璐可·xi");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的手牌比对战对手多3张以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。多6张以上的场合，作为替代，直到回合结束时为止，其的力量-6000。\n" +
                "@E :抽2张牌。\n" +
                "@A $G1 %B0对战对手的核心分身的等级以下的数字1种宣言。看对战对手的手牌，把不持有#G的持有与宣言数字相同等级的全部的精灵舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int diff = getHandCount(getOwner()) - getHandCount(getOpponent());
            
            if(diff >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, diff >= 6 ? -6000 : -3000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            draw(2);
        }
        
        private void onActionEff()
        {
            int numbers[] = new int[getLRIG(getOpponent()).getIndexedInstance().getLevel().getValue()+1];
            for(int i=0;i<numbers.length;i++) numbers[i] = i;
            
            int level = playerChoiceNumber(numbers)-1;
            
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            discard(new TargetFilter().OP().SIGNI().fromRevealed().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).withLevel(level).getExportedData());
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
    }
}

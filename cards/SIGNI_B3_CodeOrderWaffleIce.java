package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B3_CodeOrderWaffleIce extends Card {
    
    public SIGNI_B3_CodeOrderWaffleIce()
    {
        setImageSets("WXDi-P03-040");
        
        setOriginalName("コードオーダー　ワッフルアイス");
        setAltNames("コードオーダーワッフルアイス Koodo Oodaa Waffuru Aisu");
        setDescription("jp",
                "@U：対戦相手の凍結状態のシグニ１体が場を離れたとき、あなたのデッキの一番上のカードをこのシグニの下に置く。\n" +
                "@U：各アタックフェイズ開始時、このシグニの下からカード１枚を手札に加えるかエナゾーンに置く。\n" +
                "@E：対戦相手のシグニを２体まで対象とし、それらを凍結する。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );
        
        setName("en", "Waffle Ice, Code: Order");
        setDescription("en",
                "@U: Whenever a frozen SIGNI leaves your opponent's field, put the top card of your deck under this SIGNI. \n" +
                "@U: At the beginning of each attack phase, put a card underneath this SIGNI into its owner's hand or Ener Zone.\n" +
                "@E: Freeze up to two target SIGNI on your opponent's field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "Code Order Waffle Ice");
        setDescription("en_fan",
                "@U: Whenever 1 of your opponent's frozen SIGNI leaves the field, put the top card of your deck under this SIGNI.\n" +
                "@U: At the beginning of each attack phase, add 1 card from under this SIGNI to your hand or put it into the ener zone.\n" +
                "@E: Target up to 2 of your opponent's SIGNI, and freeze them." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );
        
		setName("zh_simplified", "点单代号 华夫冰淇淋");
        setDescription("zh_simplified", 
                "@U :当对战对手的冻结状态的精灵1只离场时，你的牌组最上面的牌放置到这只精灵的下面。（表向放置）\n" +
                "@U :各攻击阶段开始时，从这只精灵的下面把1张牌加入手牌或放置到能量区。\n" +
                "@E :对战对手的精灵2只最多作为对象，将这些冻结。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
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
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) &&
                    CardLocation.isSIGNI(caller.getLocation()) && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) &&
                    caller.getIndexedInstance().isState(CardStateFlag.FROZEN) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter().own().under(getCardIndex())).get();
            
            if(cardIndex != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.ENER) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putInEner(cardIndex);
                }
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
            freeze(data);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}

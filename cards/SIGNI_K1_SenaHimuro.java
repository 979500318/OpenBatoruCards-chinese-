package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_SenaHimuro extends Card {

    public SIGNI_K1_SenaHimuro()
    {
        setImageSets("WXDi-CP02-096");

        setOriginalName("氷室セナ");
        setAltNames("ヒムロセナ Himuro Sena");
        setDescription("jp",
                "@E %X %X：あなたのトラッシュから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。@@" +
                "~#：あなたのトラッシュから＜ブルアカ＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Himuro Sena");
        setDescription("en",
                "@E %X %X: Add target <<Blue Archive>> card from your trash to your hand.~{{U: At the beginning of your attack phase, put the top two cards of your opponent's deck into their trash.@@" +
                "~#Add target <<Blue Archive>> SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Sena Himuro");
        setDescription("en_fan",
                "@E %X %X: Target 1 <<Blue Archive>> card from your trash, and add it to your hand." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash.@@" +
                "~#Target 1 <<Blue Archive>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "冰室濑奈");
        setDescription("zh_simplified", 
                "@E %X %X:从你的废弃区把<<ブルアカ>>牌1张作为对象，将其加入手牌。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@" +
                "~#从你的废弃区把<<ブルアカ>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            addToHand(target);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.ReturnToDeckCost;

public final class SIGNI_K2_FuukaAikiyo extends Card {

    public SIGNI_K2_FuukaAikiyo()
    {
        setImageSets("WXDi-CP02-100");

        setOriginalName("愛清フウカ");
        setAltNames("アイキヨフウカ Aikiyo Fuuka");
        setDescription("jp",
                "@E @[トラッシュから＜ブルアカ＞のカード１枚をデッキの一番下に置く]@：あなたの＜ブルアカ＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。@@" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Aikiyo Fuuka");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your trash on the bottom of your deck]@: Target <<Blue Archive>> SIGNI on your field gets +2000 power until the end of your opponent's next end phase.~{{U: At the beginning of your attack phase, put the top two cards of your opponent's deck into their trash.@@" +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Fuuka Aikiyo");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your trash on the bottom of your deck]@: Target 1 of your <<Blue Archive>> SIGNI, and until the end of your opponent's next turn, it gets +2000 power." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash.@@" +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "爱清风香");
        setDescription("zh_simplified", 
                "@E 从废弃区把<<ブルアカ>>牌1张放置到牌组最下面:你的<<ブルアカ>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            registerEnterAbility(new ReturnToDeckCost(DeckPosition.BOTTOM, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get();
            gainPower(target, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}

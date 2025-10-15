package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_MangroveNaturalPlant extends Card {

    public SIGNI_B2_MangroveNaturalPlant()
    {
        setImageSets("WXDi-P15-092");

        setOriginalName("羅植　マングロブ");
        setAltNames("ラショクマングロブ Rashoku Mangurobu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの手札が３枚以下の場合、カードを１枚引く。\n" +
                "$$2あなたの手札が４枚以上ある場合、【エナチャージ１】をする。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Mangrove, Natural Plant");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, choose one of the following.\n$$1If you have three or fewer cards in your hand, draw a card.\n$$2If you have four or more cards in your hand, [[Ener Charge 1]]." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Mangrove, Natural Plant");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there are 3 or less cards in your hand, draw 1 card.\n" +
                "$$2 If there are 4 or more cards in your hand, [[Ener Charge 1]]." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "罗植 红树林");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，从以下的2种选1种。\n" +
                "$$1 你的手牌在3张以下的场合，抽1张牌。\n" +
                "$$2 你的手牌在4张以上的场合，[[能量填充1]]。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(2);
        setPower(8000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                if(playerChoiceMode() == 1)
                {
                    if(getHandCount(getOwner()) <= 3)
                    {
                        draw(1);
                    }
                } else if(getHandCount(getOwner()) >= 4)
                {
                    enerCharge(1);
                }
            }
        }

        private void onLifeBurstEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(cardIndex != null)
            {
                reveal(cardIndex);
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}

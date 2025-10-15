package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_LichRacerAzureDevilPrincess extends Card {

    public SIGNI_B3_LichRacerAzureDevilPrincess()
    {
        setImageSets("WXDi-P10-039");

        setOriginalName("蒼魔姫　リッチレーサー");
        setAltNames("ソウマキリッチレーサー Soumaki Ricchi Reesaa");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、カードを１枚引く。\n\n" +
                "@U：このカードが捨てられたとき、手札を１枚捨ててもよい。そうした場合、そのターン終了時、%B %Kを支払ってもよい。そうした場合、このカードをトラッシュから場に出す。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Rich Racer, Azure Evil Queen");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, draw a card.\n\n" +
                "@U: When this card is discarded, you may discard a card. If you do, at the end of that turn, you may pay %B %X. If you do, put this card from your trash onto your field." +
                "~#Put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Lich Racer, Azure Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your opponent's attack phase, draw 1 card.\n\n" +
                "@U: When you discard this card, you may discard 1 card from your hand. If you do, at the end of that turn, you may pay %B %X. If you do, put this card from your trash onto the field." +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "苍魔姬 死灵骑士");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，抽1张牌。\n" +
                "@U :当这张牌被舍弃时，可以把手牌1张舍弃。这样做的场合，那个回合结束时，可以支付%B%X。这样做的场合，这张牌从废弃区出场。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }
        
        private void onAutoEff2()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH &&
               discard(0,1).get() != null)
            {
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(getCardIndex().getLocation() == CardLocation.TRASH &&
                       payEner(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1)))
                    {
                        putOnField(getCardIndex());
                    }
                });
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_B2_AyasHolograph extends Card {

    public LRIGA_B2_AyasHolograph()
    {
        setImageSets("WXDi-P09-036");

        setOriginalName("あーやのホログラフ！");
        setAltNames("アーヤノホログラフ Aaya ni Horogurafu");
        setDescription("jp",
                "@E：ターン終了時まで、このルリグは@>@U：対戦相手のシグニかルリグ１体がアタックしたとき、あなたと対戦相手は自分のデッキの一番上を公開し、そのカードをデッキの一番下に置く。この方法で公開されたカードがどちらも##を持っているか、どちらも##を持っていない場合、そのアタックを無効にする。@@を得る。"
        );

        setName("en", "Aya's Holograph!");
        setDescription("en",
                "@E: This LRIG gains@>@U: Whenever your opponent's SIGNI or LRIG attacks, each player reveals the top card of their deck, and puts that card on the bottom of their deck. If the cards revealed this way both have ## or both do not have ##, negate that attack.@@until end of turn."
        );

        setName("en_fan", "Aya's Holograph!");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U: Whenever your opponent's SIGNI or LRIG attacks, reveal the top card of your deck and your opponent's deck, and put them on the bottom of the deck. If both of the cards revealed this way have ## @[Life Burst]@, or if both of them don't have ## @[Life Burst]@, disable that attack."
        );

		setName("zh_simplified", "亚弥的全息！");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，这只分身得到\n" +
                "@>@U 当对战对手的精灵或分身1只攻击时，你和对战对手把自己的牌组最上面公开，那张牌放置到牌组最下面。这个方法公开的牌都持有##或，都不持有##的场合，那次攻击无效。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AYA);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);

            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndexOwn = reveal();
            CardIndex cardIndexOP = reveal(getOpponent());

            if(((cardIndexOwn != null ? cardIndexOwn.getIndexedInstance().getCardStateFlags().getValue() : 0) & CardStateFlag.HAS_LIFEBURST) ==
               ((cardIndexOP != null ? cardIndexOP.getIndexedInstance().getCardStateFlags().getValue() : 0) & CardStateFlag.HAS_LIFEBURST))
            {
                disableNextAttack(caller);
            }

            returnToDeck(cardIndexOwn, DeckPosition.BOTTOM);
            returnToDeck(cardIndexOP, DeckPosition.BOTTOM);
        }
    }
}

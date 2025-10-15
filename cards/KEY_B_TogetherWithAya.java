package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class KEY_B_TogetherWithAya extends Card {

    public KEY_B_TogetherWithAya()
    {
        setImageSets("WDK04-006", "SPK02-03");

        setOriginalName("いっしょにあーや！");
        setAltNames("イッショニアーヤ Issho Ni Aaya");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@U：対戦相手のセンタールリグがアタックしたとき、対戦相手は偶数か奇数かを宣言する。あなたのデッキの上からシグニがめくれるまで公開する。この方法で公開されたシグニのレベルが宣言と異なる場合、そのアタックを無効にする。公開されたカードをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Together with Aya!");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@U: Whenever your opponent's center LRIG attacks, your opponent declares \"even\" or \"odd\". Reveal cards from the top of your deck until you reveal a SIGNI. If the level of the revealed this way SIGNI doesn't match the declared level parity, disable that attack. Shuffle the revealed by this effect cards, and put them on the bottom of your deck."
        );

		setName("zh_simplified", "一起亚弥！");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@U 当对战对手的核心分身攻击时，对战对手把偶数或奇数宣言。从你的牌组上面直到把精灵公开为止。这个方法公开的精灵的等级与宣言不同的场合，那次攻击无效。公开的牌洗切放置到牌组最下面。@@@@\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.BLUE);
        setCost(Cost.coin(1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffModGetSample));
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.getLocation() == CardLocation.LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            int parity = playerChoiceAction(getOpponent(), ActionHint.EVEN,ActionHint.ODD)-1;
            
            CardIndex cardIndexSIGNI = revealUntil(getOwner(), cardIndex -> CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()));
            if(cardIndexSIGNI != null)
            {
                int level = cardIndexSIGNI.getIndexedInstance().getLevelByRef();
                
                if(level % 2 != parity)
                {
                    disableNextAttack(caller);
                }
            }
            
            int numReturned = returnToDeck(getCardsInRevealed(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(numReturned, DeckPosition.BOTTOM);
        }
    }
}

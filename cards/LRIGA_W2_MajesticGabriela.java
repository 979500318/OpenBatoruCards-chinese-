package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_W2_MajesticGabriela extends Card {

    public LRIGA_W2_MajesticGabriela()
    {
        setImageSets("WXDi-P16-034");

        setOriginalName("煌々！！ガブリエラ");
        setAltNames("コウコウガブリエラ Koukou Gaburiera");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手は%Xを支払わないかぎりシグニでアタックできない。@@を得る。"
        );

        setName("en", "Gabriela, Brilliant!!");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Add up to two cards from among them to your hand and put the rest on the bottom of your deck in any order.\n@E: This LRIG gains@>@C: Your opponent cannot attack with SIGNI unless they pay %X.@@until the end of your opponent's next end phase. "
        );
        
        setName("en_fan", "Majestic!! Gabriela");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Add up to 2 of them to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: Your opponent's SIGNI can't attack unless your opponent pays %X."
        );

		setName("zh_simplified", "煌煌！！哲布伊来");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C 对战对手如果不把%X:支付，那么精灵不能攻击。@@\n" +
                "。（每次攻击都要支付）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            look(5);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onEnterEff2()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> new EnerCost(Cost.colorless(1)))
            );
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}

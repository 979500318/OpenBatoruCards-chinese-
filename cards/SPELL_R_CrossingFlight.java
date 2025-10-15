package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SPELL_R_CrossingFlight extends Card {

    public SPELL_R_CrossingFlight()
    {
        setImageSets("WX25-P1-079");

        setOriginalName("交差の飛来");
        setAltNames("コウサノヒライ Kousa no Hirai");
        setDescription("jp",
                "あなたのデッキの上からカードを5枚見る。その中から＜ウェポン＞のシグニ１枚を場に出し、残りを好きな順番でデッキの一番下に置く。次のあなたのアタックフェイズ開始時、次の対戦相手のターン終了時まで、あなたのクロス状態のすべてのシグニのパワーを＋3000する。" +
                "~#：対戦相手のアップ状態のシグニ1体を対象とし、それをバニッシュする。"
        );

        setName("en", "Crossing Flight");
        setDescription("en",
                "Look at the top 5 cards of your deck. Put 1 <<Weapon>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order. At the beginning of your next attack phase, until the end of your opponent's next turn, all of your crossed SIGNI get +3000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "交差的飞来");
        setDescription("zh_simplified", 
                "从你的牌组上面看5张牌。从中把<<ウェポン>>精灵1张出场，剩下的任意顺序放置到牌组最下面。下一个你的攻击阶段开始时，直到下一个对战对手的回合结束时为止，你的交错状态的全部的精灵的力量+3000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.WEAPON).fromLooked().playable()).get();
            putOnField(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            callDelayedEffect(ChronoDuration.nextPhase(getOwner(), GamePhase.ATTACK_PRE), () ->
                gainPower(new TargetFilter().own().SIGNI().crossed().getExportedData(), 3000, ChronoDuration.nextTurnEnd(getOpponent()))
            );
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}


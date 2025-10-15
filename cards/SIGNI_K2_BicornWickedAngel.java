package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.MillCost;

public final class SIGNI_K2_BicornWickedAngel extends Card {

    public SIGNI_K2_BicornWickedAngel()
    {
        setImageSets("WX25-P1-107");

        setOriginalName("凶天　バイコーン");
        setAltNames("キョウテンバイコーン Kyouten Baikoon");
        setDescription("jp",
                "@E @[デッキの上からカードを３枚トラッシュに置く]@：あなたの＜天使＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~#：対戦相手のシグニ1体を対象とし、ターン終了時まで、それのパワーを−8000する。"
        );

        setName("en", "Bicorn, Wicked Angel");
        setDescription("en",
                "@E @[Put the top 3 cards of your deck into the trash]@: Target 1 of your <<Angel>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "凶天 双角兽");
        setDescription("zh_simplified", 
                "@E 从牌组上面把3张牌放置到废弃区:你的<<天使>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new MillCost(3), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.ANGEL)).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

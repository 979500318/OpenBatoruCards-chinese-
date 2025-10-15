package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K2_MadHatterMediumTrap extends Card {

    public SIGNI_K2_MadHatterMediumTrap()
    {
        setImageSets("WDK04-015");

        setOriginalName("中罠　マッドハッター");
        setAltNames("チュウビンマッドハッター Chuubin Maddo Hattaa");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。それをデッキの一番下に置いてもよい。この方法で公開したカードがレベルが奇数のシグニの場合、を支払ってもよい。そうした場合、ターン終了時まで、このシグニのパワーは＋7000され、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。@@を得る。"
        );

        setName("en", "Mad Hatter, Medium Trap");
        setDescription("en",
                "@E: Reveal the top card of your deck. You may put it on the bottom of your deck. If it was a SIGNI with an odd level, you may pay %K. If you do, until end of turn, this SIGNI gets +7000 power, and it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "中罠 疯帽子");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。可以将其放置到牌组最下面。这个方法公开的牌是等级在奇数的精灵的场合，可以支付%K。这样做的场合，直到回合结束时为止，这只精灵的力量+7000，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY);
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
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                boolean match = CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && (cardIndex.getIndexedInstance().getLevelByRef() % 2) != 0;
                
                returnToDeck(cardIndex, playerChoiceAction(ActionHint.BOTTOM, ActionHint.TOP) == 1 ? DeckPosition.BOTTOM : DeckPosition.TOP);
                
                if(match && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(getCardIndex(), 7000, ChronoDuration.turnEnd());

                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}

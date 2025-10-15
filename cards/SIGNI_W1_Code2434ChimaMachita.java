package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_W1_Code2434ChimaMachita extends Card {

    public SIGNI_W1_Code2434ChimaMachita()
    {
        setImageSets("WXDi-CP01-033");

        setOriginalName("コード２４３４　町田ちま");
        setAltNames("コードニジサンジマチタチマ Koodo Nijisanji Machita Chima");
        setDescription("jp",
                "@E：あなたのデッキの一番下のカードをトラッシュに置く。そのカードが＜バーチャル＞のシグニの場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。そのカードが《コード２４３４　町田ちま》の場合、この効果を繰り返す。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Machita Chima, Code 2434");
        setDescription("en",
                "@E: Put the bottom card of your deck into your trash. If that card is a <<Virtual>> SIGNI, this SIGNI gets +5000 power until the end of your opponent's next end phase. If that card is \"Machita Chima, Code 2434\", repeat this effect." +
                "~#Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Code 2434 Chima Machita");
        setDescription("en_fan",
                "@E: Put the bottom card of your deck into the trash. If it is a <<Virtual>> SIGNI, until the end of your opponent's next turn, this SIGNI gets +5000 power. If that card is \"Code 2434 Chima Machita\", repeat this effect." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "2434代号 町田千麻");
        setDescription("zh_simplified", 
                "@E :你的牌组最下面的牌放置到废弃区。那张牌是<<バーチャル>>精灵的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。那张牌是《コード２４３４　町田ちま》的场合，这个效果重复。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(2000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(getOwner(), 1, DeckPosition.BOTTOM).get();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
            {
                if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL))
                {
                    gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                }
                if(cardIndex.getIndexedInstance().getName().getValue().contains("コード２４３４　町田ちま"))
                {
                    onEnterEff();
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

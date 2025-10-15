package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_Code2434YotsuhaUmise extends Card {

    public SIGNI_K1_Code2434YotsuhaUmise()
    {
        setImageSets("WXDi-CP01-046");

        setOriginalName("コード２４３４　海妹四葉");
        setAltNames("コードニジサンジウミセヨツハ Koodo Nijisanji Umise Yotsuha");
        setDescription("jp",
                "@E：あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それをデッキの一番下に置く。そうした場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Umise Yotsuha, Code 2434");
        setDescription("en",
                "@E: Put target <<Virtual>> SIGNI from your trash on the bottom of your deck. If you do, this SIGNI gets +4000 power until the end of your opponent's next end phase." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Yotsuha Umise");
        setDescription("en_fan",
                "@E: Target 1 <<Virtual>> SIGNI from your trash, and put it on the bottom of your deck. If you do, until the end of your opponent's next turn, this SIGNI gets +4000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "2434代号 海妹四叶");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把<<バーチャル>>精灵1张作为对象，将其放置到牌组最下面。这样做的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(3000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash()).get();
            
            if(returnToDeck(target, DeckPosition.BOTTOM))
            {
                gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

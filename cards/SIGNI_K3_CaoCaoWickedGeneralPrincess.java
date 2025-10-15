package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K3_CaoCaoWickedGeneralPrincess extends Card {

    public SIGNI_K3_CaoCaoWickedGeneralPrincess()
    {
        setImageSets("WXDi-P16-053");

        setOriginalName("凶将姫　ソウソウ");
        setAltNames("キョウショウキソウソウ Kyoushouki Sousou");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のデッキの上からカードを４枚トラッシュに置く。その後、対戦相手のトラッシュにあるいずれかのカードと同じ名前の対戦相手のシグニ１体を対象とし、%K %K %Xを支払ってもよい。そうした場合、それをバニッシュする。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Cao Cao, Doomed General Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, put the top four cards of your opponent's deck into their trash. Then, you may pay %K %K %X. If you do, vanish target SIGNI on your opponent's field with the same name as a card in your opponent's trash." +
                "~#Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Cao Cao, Wicked General Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, put the top 4 cards of your opponent's deck into the trash. Then, target 1 of your opponent's SIGNI with the same name as any card in your opponent's trash, and you may pay %K %K %X. If you do, banish it." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );

		setName("zh_simplified", "凶将姬 曹操");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，从对战对手的牌组上面把4张牌放置到废弃区。然后，与对战对手的废弃区的任一张的牌相同名字的对战对手的精灵1只作为对象，可以支付%K %K%X。这样做的场合，将其破坏。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            millDeck(getOpponent(), 4);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withName(
                new TargetFilter().OP().fromTrash().getExportedData().stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).toArray(String[]::new))
            ).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 2) + Cost.colorless(1)))
            {
                banish(target);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}

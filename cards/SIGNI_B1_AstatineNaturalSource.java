package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_AstatineNaturalSource extends Card {

    public SIGNI_B1_AstatineNaturalSource()
    {
        setImageSets("WX25-P1-081");

        setOriginalName("羅原　At");
        setAltNames("ラゲンアスタチン Ragen Asutachin");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Astatine, Natural Source");
        setDescription("en",
                "@U: When this SIGNI is banished, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "罗原 At");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}

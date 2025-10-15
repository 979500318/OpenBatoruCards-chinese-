package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_MitsukiSetoDenonbu extends Card {

    public SIGNI_B2_MitsukiSetoDenonbu()
    {
        setImageSets("WXDi-P14-084");

        setOriginalName("電音部　瀬戸海月");
        setAltNames("デンオンブセトミツキ Denonbu Seto Mitsuki");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札から＜電音部＞のシグニを１枚捨ててもよい。そうした場合、カードを２枚引く。"
        );

        setName("en", "DEN-ON-BU Mitsuki Seto");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard a <<DEN-ON-BU>> SIGNI. If you do, draw two cards."
        );
        
        setName("en_fan", "Mitsuki Seto, Denonbu");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 1 <<Denonbu>> SIGNI from your hand. If you do, draw 2 cards."
        );

		setName("zh_simplified", "电音部 濑户海月");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从手牌把<<電音部>>精灵1张舍弃。这样做的场合，抽2张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(2);
        setPower(5000);

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
        }
        
        private void onAutoEff()
        {
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU)).get() != null)
            {
                draw(2);
            }
        }
    }
}

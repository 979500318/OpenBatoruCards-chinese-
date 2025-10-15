package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_B3_TamagoDissonaNaturalStarPrincess extends Card {

    public SIGNI_B3_TamagoDissonaNaturalStarPrincess()
    {
        setImageSets("WXDi-P12-049", "WXDi-P12-049P");

        setOriginalName("羅星姫　タマゴ//ディソナ");
        setAltNames("ラセイキタマゴディソナ Raseiki Tamago Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札を１枚捨ててもよい。そうした場合、カードを２枚引く。\n" +
                "@A @[アップ状態のルリグ２体をダウンする]@：カードを１枚引く。"
        );

        setName("en", "Tamago//Dissona, Planet Queen");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard a card. If you do, draw two cards.\n@A @[Down two upped LRIG]@: Draw a card."
        );
        
        setName("en_fan", "Tamago//Dissona, Natural Star Princess");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 1 card from your hand. If you do, draw 2 cards.\n" +
                "@A @[Down 2 of your upped LRIG]@: Draw 1 card."
        );

		setName("zh_simplified", "罗星姬 玉子//失调");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以把手牌1张舍弃。这样做的场合，抽2张牌。\n" +
                "@A 竖直状态的分身2只#D:抽1张牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerActionAbility(new DownCost(2, new TargetFilter().upped().anyLRIG()), this::onActionEff);
        }
        
        private void onAutoEff()
        {
            if(discard(0,1).get() != null)
            {
                draw(2);
            }
        }
        
        private void onActionEff()
        {
            draw(1);
        }
    }
}

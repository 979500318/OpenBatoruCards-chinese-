package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass.CardSIGNIClassValue;
import open.batoru.data.Cost;
import open.batoru.data.ability.CardAbilities;

import java.util.ArrayList;
import java.util.List;

public final class PIECE_X_CountdownHeels extends Card {

    public PIECE_X_CountdownHeels()
    {
        setImageSets("WXDi-P09-004", "PR-Di027");

        setOriginalName("カウントダウン・ヒールズ");
        setAltNames("カウントダウンヒールズ Kauntodaun Hiiruzu");
        setDescription("jp",
                "クラス１つを宣言する。その後、あなたのトラッシュから宣言したクラスを持ち#Gを持たない、レベル１、レベル２、レベル３のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Countdown Heels");
        setDescription("en",
                "Declare a class. Then, add up to one target level one SIGNI, one target level two SIGNI, and one target level three SIGNI of the declared class without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Countdown Heels");
        setDescription("en_fan",
                "Declare 1 SIGNI class. Target up to 1 level 1 SIGNI, up to 1 level 2 SIGNI, and up to 1 level 3 SIGNI each with the declared class and without #G @[Guard]@ from your trash, and add them to your hand."
        );

		setName("zh_simplified", "倒计·高跟");
        setDescription("zh_simplified", 
                "类别1种宣言。然后，从你的废弃区把持有宣言的类别且不持有#G的，等级1、等级2、等级3的精灵各1张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerPieceAbility(this::onPieceEff);
        }

        private void onPieceEff()
        {
            List<CardSIGNIClass> listClasses = new ArrayList<>();
            List<CardSIGNIClassValue> cardSIGNIClassValues = CardAbilities.getSIGNIClasses(getCardsInTrash(getOwner()));

            for(CardSIGNIClassValue cardSIGNIClassValue : cardSIGNIClassValues)
            {
                if(listClasses.contains(cardSIGNIClassValue.cardSIGNIClass())) continue;
                listClasses.add(cardSIGNIClassValue.cardSIGNIClass());
            }

            CardSIGNIClass cardSIGNIClassChosen = cardSIGNIClassValues.size() == 1 ? cardSIGNIClassValues.getFirst().cardSIGNIClass() :
                                                                                     playerChoiceSIGNIClass(listClasses.toArray(CardSIGNIClass[]::new));

            DataTable<CardIndex> data = new DataTable<>();
            for(int i=1;i<=3;i++)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(i).withClass(cardSIGNIClassChosen).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                if(cardIndex != null) data.add(cardIndex);
            }
            addToHand(data);
        }
    }
}

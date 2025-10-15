package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_EinConotoxiDissona extends Card {

    public SIGNI_G1_EinConotoxiDissona()
    {
        setImageSets("WXDi-P13-078");

        setOriginalName("アイン＝コノトキ//ディソナ");
        setAltNames("アインコノトキディソナ Ain Konotoki Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にパワー10000以上の《ディソナアイコン》のシグニがある場合、【エナチャージ１】をする。"
        );

        setName("en", "Konotoxin//Dissona Type: Eins");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a #S SIGNI on your field with power 10000 or more, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Ein-Conotoxi//Dissona");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if you have a #S @[Dissona]@ SIGNI with power 10000 or more, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "EINS=芋螺毒素//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有力量10000以上的#S的精灵的场合，[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(2000);

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
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().dissona().withPower(10000,0).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
    }
}

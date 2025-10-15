package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_W1_TawilDissonaHolyAngel extends Card {

    public SIGNI_W1_TawilDissonaHolyAngel()
    {
        setImageSets("WXDi-P12-056", "SPDi01-82");

        setOriginalName("聖天　タウィル//ディソナ");
        setAltNames("セイテンタウィルディソナ Seiten Tauiru Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのエナゾーンとトラッシュに#Sのカードが合計７枚以上ある場合、カードを１枚引く。"
        );

        setName("en", "Tawil//Dissona, Blessed Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are seven or more #S cards in your trash and Ener Zone in total, draw a card."
        );
        
        setName("en_fan", "Tawil//Dissona, Holy Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 7 or more #S @[Dissona]@ cards in your ener zone and/or trash, draw 1 card."
        );

		setName("zh_simplified", "圣天 塔维尔//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的能量区和废弃区的#S的牌合计7张以上的场合，抽1张牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            if((new TargetFilter().own().dissona().fromEner().getValidTargetsCount() + new TargetFilter().own().dissona().fromTrash().getValidTargetsCount()) >= 7)
            {
                draw(1);
            }
        }
    }
}

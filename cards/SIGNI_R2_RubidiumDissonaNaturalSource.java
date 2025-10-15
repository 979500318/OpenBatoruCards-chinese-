package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_RubidiumDissonaNaturalSource extends Card {

    public SIGNI_R2_RubidiumDissonaNaturalSource()
    {
        setImageSets("WXDi-P13-066");

        setOriginalName("羅原　Ｒｂ//ディソナ");
        setAltNames("ラゲンルビジウムディソナ Ragen Rubijiumu Disona Rb");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に#Sのシグニが３体ある場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Rb//Dissona, Natural Element");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are three #S SIGNI on your field, vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Rubidium//Dissona, Natural Source");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 3 #S @[Dissona]@ SIGNI on your field, target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );

		setName("zh_simplified", "罗原 Rb//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上的#S的精灵在3只的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
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
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                banish(target);
            }
        }
    }
}


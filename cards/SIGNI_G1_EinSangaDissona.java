package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_EinSangaDissona extends Card {

    public SIGNI_G1_EinSangaDissona()
    {
        setImageSets("WXDi-P12-076", "SPDi01-87");

        setOriginalName("アイン＝サンガ//ディソナ");
        setAltNames("アインサンガディソナ Ain Sanga Disona");
        setDescription("jp",
                "@C：あなたの場にあるシグニが持つ色が合計３種類以上あるかぎり、このシグニのパワーは＋4000される。\n" +
                "@A #D：あなたのエナゾーンから#Sのシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Sanga//Dissona Type: Eins");
        setDescription("en",
                "@C: As long as you have three or more different colors among SIGNI on your field, this SIGNI gets +4000 power.\n@A #D: Put target #S SIGNI from your Ener Zone onto your field."
        );
        
        setName("en_fan", "Ein-Sanga//Dissona");
        setDescription("en_fan",
                "@C: As long as there are 3 or more colors among SIGNI on your field, this SIGNI gets +4000 power.\n" +
                "@A #D: Target 1 #S @[Dissona]@ SIGNI from your ener zone, and put it onto the field."
        );

		setName("zh_simplified", "EINS=山河//失调");
        setDescription("zh_simplified", 
                "@C :你的场上的精灵持有颜色在合计3种类以上时，这只精灵的力量+4000。\n" +
                "@A :横置从你的能量区把#S的精灵1张作为对象，将其出场。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return CardAbilities.getColorsCount(getSIGNIOnField(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().fromEner().playable()).get();
            putOnField(target);
        }
    }
}

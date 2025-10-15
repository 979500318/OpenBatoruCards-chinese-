package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.*;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;

public final class LRIG_K3_TamayorihimeBlackRemnantMiko extends Card {

    public LRIG_K3_TamayorihimeBlackRemnantMiko()
    {
        setImageSets("WXDi-P12-010", "WXDi-P12-010U");

        setOriginalName("残黒の巫女　タマヨリヒメ");
        setAltNames("ザンコクノミコタマヨリヒメ Zankoku no Miko Tamayorihime");
        setDescription("jp",
                "@C：あなたの、場とエナゾーンにある#Sのシグニは追加で黒を得る。\n" +
                "@A $T1 %K %K：あなたの場に#Sのシグニが２体以上ある場合、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "@A @[エクシード４]@：あなたのトラッシュから#Sのシグニを３枚まで対象とし、それらを場に出す。"
        );

        setName("en", "Tamayorihime, Enduring Darkness Miko");
        setDescription("en",
                "@C: #S SIGNI on your field and in your Ener Zone are additionally black.\n@A $T1 %K %K: If there are two or more #S SIGNI on your field, put target SIGNI on your opponent's field into its owner's trash.\n@A @[Exceed 4]@: Put up to three target #S SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Tamayorihime, Black Remnant Miko");
        setDescription("en_fan",
                "@C: All of the #S @[Dissona]@ SIGNI on your field and in your ener zone are also black.\n" +
                "@A $T1 %K %K: If there are 2 or more #S @[Dissona]@ SIGNI on your field, target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "@A @[Exceed 4]@: Target up to 3 #S @[Dissona]@ SIGNI from your trash, and put them onto the field."
        );

		setName("zh_simplified", "残黑的巫女 玉依姬");
        setDescription("zh_simplified", 
                "@C 你的，场上和能量区的#S的精灵追加得到黑色。\n" +
                "@A $T1 %K %K你的场上的#S的精灵在2只以上的场合，对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "@A @[超越 4]@从你的废弃区把#S的精灵3张最多作为对象，将这些出场。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(
                new TargetFilter().own().SIGNI().dissona().fromLocation(
                    CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT,CardLocation.CHEER,
                    CardLocation.ENER
                ),
                new ModifiableValueModifier<>(this::onConstEffModGetSample, () -> CardColor.BLACK)
            );

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 2)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }
        
        private CardDataColor onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
        
        private void onActionEff1()
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                trash(target);
            }
        }
        
        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().fromTrash().playable());
            putOnField(data);
        }
    }
}

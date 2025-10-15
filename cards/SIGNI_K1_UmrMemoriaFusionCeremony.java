package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_K1_UmrMemoriaFusionCeremony extends Card {

    public SIGNI_K1_UmrMemoriaFusionCeremony()
    {
        setImageSets("WXDi-P11-078", "WXDi-P11-078P");
        setLinkedImageSets("WXDi-P11-051");

        setOriginalName("融合の儀　ウムル//メモリア");
        setAltNames("ユウゴウノギウムルメモリア Yuugou no Gi Umuru Memoria");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。そのカードが《融合の儀　タウィル//メモリア》の場合、そのカードをトラッシュから場に出してもよい。\n" +
                "@A %K %W @[このシグニと《融合の儀　タウィル//メモリア》１体を場からトラッシュに置く]@：あなたのトラッシュから白か黒のシグニ１枚を対象とし、それを場に出す。\n\n" +
                "@C：このカードの上にある《融合せし極門　ウトゥルス//メモリア》は@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。@@を得る。"
        );

        setName("en", "Umr//Memoria, Fusion Ritual");
        setDescription("en",
                "@E: Put the top card of your deck into your trash. If that card is \"Tawil//Memoria, Fusion Ritual\", you may put that card from your trash onto your field.\n" +
                "@A %K %W @[Put this SIGNI and a \"Tawil//Memoria, Fusion Ritual\" into their owner's trash]@: Put target white or black SIGNI from your trash onto your field.\n\n" +
                "@C: The \"Ut'ulls//Memoria, Fusion Ultra Gate\" on top of this card gains@>@U: Whenever this SIGNI attacks, target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Umr//Memoria, Fusion Ceremony");
        setDescription("en_fan",
                "@E: Put the top card of your deck into the trash. If that card is \"Tawil//Memoria, Ritual Fusion\", you may put that card from your trash onto the field.\n" +
                "@A %K %W @[Put this SIGNI and 1 \"Tawil//Memoria, Fusion Ritual\" from the field into the trash]@: Target 1 white or black SIGNI from your trash, and put it onto the field.\n\n" +
                "@C: The \"Ut'ulls//Memoria, Fused Ultimate Gate\" on top of this card gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "融合之仪 乌姆尔//回忆");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到废弃区。那张牌是《融合の儀　タウィル//メモリア》的场合，可以把那张牌从废弃区出场。\n" +
                "@A %K%W这只精灵和《融合の儀　タウィル//メモリア》1只从场上放置到废弃区:从你的废弃区把白色或黑色的精灵1张作为对象，将其出场。\n" +
                "@C :这张牌的上面的《融合せし極門　ウトゥルス//メモリア》得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.WHITE, 1)),
                new TrashCost(),
                new TrashCost(new TargetFilter().SIGNI().withName("融合の儀　タウィル//メモリア"))
            ), this::onActionEff);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().own().SIGNI().withName("融合せし極門　ウトゥルス//メモリア").over(cardId),
                new AbilityGainModifier(this::onConstEffModGetSample)
            );
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex != null && cardIndex.getIndexedInstance().getName().getValue().contains("融合の儀　タウィル//メモリア") &&
               cardIndex.getLocation() == CardLocation.TRASH && cardIndex.getIndexedInstance().isPlayable() && playerChoiceActivate())
            {
                putOnField(cardIndex);
            }
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE,CardColor.BLACK).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = getAbility().getSourceCardIndex().getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

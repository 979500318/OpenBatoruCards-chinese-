package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_WK3_UtullsMemoriaFusedUltimateGate extends Card {

    public SIGNI_WK3_UtullsMemoriaFusedUltimateGate()
    {
        setImageSets("WXDi-P11-050", "WXDi-P11-050P");
        setLinkedImageSets("WXDi-P11-051","WXDi-P11-078");

        setOriginalName("融合せし極門　ウトゥルス//メモリア");
        setAltNames("ユウゴウセシゴクモンウトゥルスメモリア Yuugouseshi Gokumon Uturusu Memoria Utulls");
        setDescription("jp",
                "@C：このシグニは《融合の儀　タウィル//メモリア》か《融合の儀　ウムル//メモリア》の効果によってしか新たに場に出せない。\n" +
                "@E：あなたのトラッシュから《融合の儀　タウィル//メモリア》か《融合の儀　ウムル//メモリア》１枚を対象とし、それをこのシグニの下に置く。\n" +
                "@E：あなたのトラッシュから白か黒のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Ut'ulls//Memoria, Fusion Ultra Gate");
        setDescription("en",
                "@C: This SIGNI can only be entered onto the field by the effects of \"Tawil//Memoria, Fusion Ritual\" or \"Umr//Memoria, Fusion Ritual\".\n" +
                "@E: Put target \"Tawil//Memoria, Fusion Ritual\" or \"Umr//Memoria, Fusion Ritual\" from your trash under this SIGNI.\n" +
                "@E: Put target white or black SIGNI from your trash onto your field." +
                "~#Add target SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Ut'ulls//Memoria, Fused Ultimate Gate");
        setDescription("en_fan",
                "@C: This SIGNI can only be newly put onto the field with the effect of \"Umr//Memoria, Fusion Ceremony\" or \"Tawil//Memoria, Fusion Ceremony\".\n" +
                "@E: Target 1 \"Umr//Memoria, Fusion Ceremony\" or \"Tawil//Memoria, Fusion Ceremony\" from your trash, and put it under this SIGNI.\n" +
                "@E: Target 1 white or black SIGNI from your trash, and put it onto the field." +
                "~#Target 1 SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "融合的极门 乌托鲁斯//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵只能因为《融合の儀　タウィル//メモリア》或《融合の儀　ウムル//メモリア》的效果新出场。\n" +
                "@E :从你的废弃区把《融合の儀　タウィル//メモリア》或《融合の儀　ウムル//メモリア》1张作为对象，将其放置到这只精灵的下面。\n" +
                "@E :从你的废弃区把白色或黑色的精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(15000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont = registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_NEWLY_PUT_ON_FIELD, this::onConstEffModRuleCheck));
            cont.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null &&
                   (data.getSourceCardIndex().getIndexedInstance().getName().getValue().contains("融合の儀　タウィル//メモリア") ||
                    data.getSourceCardIndex().getIndexedInstance().getName().getValue().contains("融合の儀　ウムル//メモリア")) ? RuleCheckState.IGNORE : RuleCheckState.BLOCK;
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withName("融合の儀　タウィル//メモリア", "融合の儀　ウムル//メモリア").fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE,CardColor.BLACK).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().fromTrash()).get();

            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}

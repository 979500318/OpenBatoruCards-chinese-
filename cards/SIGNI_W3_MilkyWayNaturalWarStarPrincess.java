package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataType;
import open.batoru.data.ValueByReference;
import open.batoru.data.ValueByReferenceOptions;
import open.batoru.data.ValueByReferenceOptions.OptionType;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.ValueByReferenceModifier;

public final class SIGNI_W3_MilkyWayNaturalWarStarPrincess extends Card {

    public SIGNI_W3_MilkyWayNaturalWarStarPrincess()
    {
        setImageSets("WX25-P2-052", "WX25-P2-052U");
        setLinkedImageSets("WX25-P2-014");

        setOriginalName("羅星闘姫　ミルキィウェイ");
        setAltNames("ラセイトウキミルキィウェイ Raseitouki Mirukyiuei");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に《明星の使者　サシェ・モティエ》がいる場合、パワーがこのシグニのパワーの半分以下の対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $T1 @[エナゾーンから＜宇宙＞のシグニ２枚をトラッシュに置く]@：次の対戦相手のターン終了時まで、このシグニのパワーは＋10000され、このシグニは@>@C：あなたの効果１つによってこのシグニを参照する場合、レゾナとしても扱う。@@を得る。"
        );

        setName("en", "Milky Way, Natural War Star Princess");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Sashe Moitié, Morning Star Messanger\", target 1 of your opponent's SIGNI with power equal to or less than half this SIGNI's power, and return it to their hand.\n" +
                "@A $T1 @[Put 2 <<Space>> SIGNI from your ener zone into the trash]@: Until the end of your opponent's next turn, this SIGNI gets +10000 power, and it gains:" +
                "@>@C: If one of your effects would refer to this SIGNI, treat it as a Resona."
        );

		setName("zh_simplified", "罗星斗姬 银河");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有《明星の使者　サシェ・モティエ》的场合，力量在这只精灵的力量的一半以下的对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@A $T1 从能量区把<<宇宙>>精灵2张放置到废弃区:直到下一个对战对手的回合结束时为止，这只精灵的力量+10000，这只精灵得到\n" +
                "@>@C :因为你的效果1个把这只精灵参照的场合，也视作共鸣。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act = registerActionAbility(new TrashCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.SPACE).fromEner()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("明星の使者　サシェ・モティエ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0, getPower().getValue()/2)).get();
                addToHand(target);
            }
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 10000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            ConstantAbility attachedConst = new ConstantAbility(new ValueByReferenceModifier<>(this::onAttachedConstEffModGetSample,
                new ValueByReference<>(this::onAttachedConstEffModByRefCond, new ValueByReferenceOptions<>(OptionType.REPLACE_DEFAULT, CardType.RESONA)))
            );
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private CardDataType onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getCardType();
        }
        private boolean onAttachedConstEffModByRefCond(CardIndex cardIndex, Ability sourceAbility)
        {
            return isOwnCard(sourceAbility.getSourceCardIndex());
        }
    }
}
